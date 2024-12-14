package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

@Data
public class PerformanceInfo {
    /**
     * 首次内容绘制 (FCP) 时间。
     */
    private Double fcp;

    /**
     * 白屏时间（FP）。
     */
    private Integer fp;

    /**
     * 重定向耗时。
     */
    private Integer redirectTime;

    /**
     * DNS查询耗时。
     */
    private Integer domainLookupTime;

    /**
     * TCP连接耗时。
     */
    private Integer connectTime;

    /**
     * HTTP请求耗时。
     */
    private Integer responseTime;

    /**
     * DOM解析耗时。
     */
    private Integer domCompleteTime;

    /**
     * 第一字节时间 (TTFB)。
     */
    private Double ttfb;
}