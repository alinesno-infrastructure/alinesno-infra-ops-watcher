package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

@Data
public class PerformanceInfo {
    /**
     * 首次绘制时间 (FP)。
     */
    private Integer fp;

    /**
     * 重定向耗时。
     */
    private Integer redirect_time;

    /**
     * DNS 查询耗时。
     */
    private Integer domain_lookup_time;

    /**
     * TCP 连接耗时。
     */
    private Integer connect_time;

    /**
     * HTTP 请求耗时。
     */
    private Integer response_time;

    /**
     * DOM 解析完成时间。
     */
    private Integer dom_complete_time;

    /**
     * 首次内容绘制时间 (FCP)。
     */
    private Double fcp;

    /**
     * 第一字节时间 (TTFB)。
     */
    private Double ttfb;

    /**
     * 最大内容绘制时间 (LCP)。
     */
    private Double lcp;

    /**
     * 首次输入延迟 (FID)。
     */
    private Double fid;
}