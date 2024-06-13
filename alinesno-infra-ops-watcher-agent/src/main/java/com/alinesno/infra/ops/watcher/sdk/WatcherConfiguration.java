package com.alinesno.infra.ops.watcher.sdk;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 初始化环境变量
 */
@Slf4j
@Data
public class WatcherConfiguration implements Serializable {

    /**
     * 项目代码
     */
    private String projectCode ;

    /**
     * 服务请求地址
     */
    private String serverUrl ;

    /**
     * 最大请求等待队列大小
     */
    private Integer maxRequestQueueSize;

    /**
     * 最大异步线程池大小
     */
    private Integer maxAsyncThreadSize;

    /**
     * 最大异步线程池队列大小
     */
    private Integer maxAsyncQueueSize;

    /**
     * 是否自动重定向开关
     */
    private boolean autoRedirection = true;

    /**
     * 全局的请求超时时间，单位为毫秒
     */
    private Integer timeout;

    /**
     * 全局的请求数据字符集
     */
    private String charset = "UTF-8";

    /**
     * 全局的请求连接超时时间，单位为毫秒
     */
    private Integer connectTimeout;

    /**
     * 全局的请求读取超时时间，单位为毫秒
     */
    private Integer readTimeout;

    /**
     * 全局的最大请求失败重试次数
     */
    private Integer maxRetryCount;

    /**
     * 全局的最大请求重试之间的时间间隔，单位为毫秒
     */
    private long maxRetryInterval;

    /**
     * 是否允许打印请求/响应日志
     */
    private boolean logEnabled = true;

    /**
     * 是否允许打印请求/响应日志
     */
    private boolean logRequest = true;


    /**
     * 是否允许打印响应状态日志
     */
    private boolean logResponseStatus = true;

    /**
     * 是否允许打印响应内容日志
     */
    private boolean logResponseContent = false;


    public static WatcherConfiguration createConfiguration() {
        final WatcherConfiguration configuration = new WatcherConfiguration();

        configuration.setTimeout(3000);
        configuration.setMaxRetryCount(0);
        configuration.setMaxRetryInterval(0);

        return configuration;
    }
}
