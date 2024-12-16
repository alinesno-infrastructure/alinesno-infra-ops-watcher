package com.alinesno.infra.ops.watcher.collector.receiver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {
    private String host = "127.0.0.1";
    private int port = 6379;
    private String password;
}