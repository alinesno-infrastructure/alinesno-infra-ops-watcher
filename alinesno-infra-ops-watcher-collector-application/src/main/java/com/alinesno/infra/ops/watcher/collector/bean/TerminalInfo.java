package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

@Data
public class TerminalInfo {
    /**
     * 终端分辨率。
     */
    private String resolving_power;

    /**
     * 文档来源页面的URL。
     */
    private String referrer;

    /**
     * 用户代理字符串。
     */
    private String ua;

    /**
     * 浏览器信息。
     */
    private BrowserInfo browser;

    /**
     * 浏览器引擎信息。
     */
    private EngineInfo engine;

    /**
     * 操作系统信息。
     */
    private OsInfo os;

    /**
     * 设备信息。
     */
    private DeviceInfo device;

    /**
     * CPU信息。
     */
    private CpuInfo cpu;

    /**
     * IP地址。
     */
    private String ip;

    /**
     * 城市信息。
     */
    private String city;
}