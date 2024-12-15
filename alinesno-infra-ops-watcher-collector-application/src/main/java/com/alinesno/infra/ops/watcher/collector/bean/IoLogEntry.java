package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;
import java.util.List;

@Data // Lombok will generate getters, setters, toString(), equals(), and hashCode()
public class IoLogEntry {
    /**
     * 页面或资源的URL。
     */
    private String name;

    /**
     * 性能条目的类型（例如："navigation"）。
     */
    private String entryType;

    /**
     * 性能条目开始的时间戳，相对于PerformanceTiming.navigationStart。
     */
    private double startTime;

    /**
     * 性能条目持续时间，单位为毫秒。
     */
    private double duration;

    /**
     * 引发请求的类型（例如：通过链接、脚本等）。
     */
    private String initiatorType;

    /**
     * 使用的协议（例如："http/1.1"）。
     */
    private String nextHopProtocol;

    /**
     * 渲染阻塞状态。
     */
    private String renderBlockingStatus;

    /**
     * Worker线程启动时间。
     */
    private double workerStart;

    /**
     * 重定向开始时间。
     */
    private double redirectStart;

    /**
     * 重定向结束时间。
     */
    private double redirectEnd;

    /**
     * 开始获取时间。
     */
    private double fetchStart;

    /**
     * DNS查询开始时间。
     */
    private double domainLookupStart;

    /**
     * DNS查询结束时间。
     */
    private double domainLookupEnd;

    /**
     * TCP连接开始时间。
     */
    private double connectStart;

    /**
     * TCP连接结束时间。
     */
    private double connectEnd;

    /**
     * 安全连接开始时间（对于HTTPS）。
     */
    private double secureConnectionStart;

    /**
     * 请求开始发送时间。
     */
    private double requestStart;

    /**
     * 第一个字节响应接收时间。
     */
    private double responseStart;

    /**
     * 最后一个字节响应接收时间。
     */
    private double responseEnd;

    /**
     * 资源传输大小，包含头部信息。
     */
    private int transferSize;

    /**
     * 编码后的主体大小。
     */
    private int encodedBodySize;

    /**
     * 解码后的主体大小。
     */
    private int decodedBodySize;

    /**
     * 服务器性能计时数据。
     */
    private List<?> serverTiming; // Assuming it's a list of some objects

    /**
     * 卸载事件开始时间。
     */
    private double unloadEventStart;

    /**
     * 卸载事件结束时间。
     */
    private double unloadEventEnd;

    /**
     * DOM解析交互完成时间。
     */
    private double domInteractive;

    /**
     * DOMContentLoaded事件触发开始时间。
     */
    private double domContentLoadedEventStart;

    /**
     * DOMContentLoaded事件触发结束时间。
     */
    private double domContentLoadedEventEnd;

    /**
     * DOM解析完全完成时间。
     */
    private double domComplete;

    /**
     * 页面加载事件开始时间。
     */
    private double loadEventStart;

    /**
     * 页面加载事件结束时间。
     */
    private double loadEventEnd;

    /**
     * 导航类型（例如："reload"）。
     */
    private String type;

    /**
     * 重定向次数。
     */
    private int redirectCount;

    /**
     * 激活开始时间。
     */
    private double activationStart;

    /**
     * 日志类型标识符。
     */
    private String logType;

    /**
     * 时间戳，用于记录日志创建时间。
     */
    private long timeStep;
}