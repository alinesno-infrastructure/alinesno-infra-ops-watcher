package com.alinesno.infra.ops.watcher.collector.receiver;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.enums.LoggerTypeEnums;
import com.alinesno.infra.ops.watcher.collector.receiver.bean.FilebeatLogEntry;
import com.alinesno.infra.ops.watcher.collector.receiver.bean.GenericLogEntry;
import com.alinesno.infra.ops.watcher.collector.receiver.parser.NginxAccessLogParser;
import com.alinesno.infra.ops.watcher.collector.receiver.parser.SpringBootLogParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class RedisQueueMonitor {

    private final RedisProperties redisProperties;
    private final TopicProperties topicProperties;

    private static final NginxAccessLogParser nginxAccessLogParser = new NginxAccessLogParser() ;
    private static final SpringBootLogParser springBootLogParser = new SpringBootLogParser() ;

    @Autowired
    public RedisQueueMonitor(RedisProperties redisProperties, TopicProperties topicProperties) {
        this.redisProperties = redisProperties;
        this.topicProperties = topicProperties;
    }

    /**
     * 初始化时启动监听器
     */
    @PostConstruct
    public void initListener() {
        new Thread(this::startSubscription).start();
        log.debug("RedisQueueMonitor started.");
    }

    private void startSubscription() {
        try (Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort())) {
            if (redisProperties.getPassword() != null && !redisProperties.getPassword().isEmpty()) {
                jedis.auth(redisProperties.getPassword());
            }
            // 订阅模式匹配，这里假设我们对所有以指定前缀开头的消息都感兴趣
            JedisPubSub pubSubListener = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("接收消息 --->>> New message in {}: {}", channel, message);
                }

                @Override
                public void onPMessage(String pattern, String channel, String message) {
                    log.info("接收消息 --->>> Pattern matched message in {}: {}", channel, message);

                    LogProcessor.processLog(message, channel, topicProperties);
                }
            };

            // 在另一个线程中运行订阅者
            String pattern = topicProperties.getPrefix() + "*";
            jedis.psubscribe(pubSubListener, pattern); // 监听指定前缀的频道
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 处理日志的静态内部类
     */
    static class LogProcessor {

        public static void processLog(String message, String channel, TopicProperties topicProperties) {
            try {
                FilebeatLogEntry filebeatLogEntry = JSONObject.parseObject(message, FilebeatLogEntry.class);
                String genericLogEntry = filebeatLogEntry.getMessage();

                if (genericLogEntry == null || genericLogEntry.isEmpty()) {
                    log.warn("Empty or null log message received.");
                    return;
                }

                if (channel != null && topicProperties.getPrefix() != null) {
                    if (channel.startsWith(topicProperties.getPrefix() + "." + LoggerTypeEnums.NGINX.getType())) {  // nginx日志
                        nginxAccessLogParser.setFilebeatLog(filebeatLogEntry);
                        try {
                            nginxAccessLogParser.parse(genericLogEntry);
                        } catch (Exception e) {
                            log.error("Error parsing NGINX log: " + e.getMessage());
                        }
                    } else if (channel.startsWith(topicProperties.getPrefix() + "." + LoggerTypeEnums.SPRINGBOOT.getType())) {  // springboot日志
                        springBootLogParser.setFilebeatLog(filebeatLogEntry);
                        try {
                            springBootLogParser.parse(genericLogEntry);
                        } catch (Exception e) {
                            log.error("Error parsing Spring Boot log: " + e.getMessage());
                        }
                    } else {
                        log.warn("Unknown log type: " + channel);
                    }
                } else {
                    log.warn("Channel or prefix is null.");
                }
            } catch (JSONException e) {
                log.error("Error parsing JSON message: " + e.getMessage());
            }
        }
    }

}