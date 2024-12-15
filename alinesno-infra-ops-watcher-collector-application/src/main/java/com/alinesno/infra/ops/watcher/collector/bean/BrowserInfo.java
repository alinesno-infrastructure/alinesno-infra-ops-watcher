package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

@Data
public class BrowserInfo {
    /**
     * 浏览器名称。
     */
    private String name;

    /**
     * 浏览器版本号。
     */
    private String version;

    /**
     * 浏览器主版本号。
     */
    private String major;
}