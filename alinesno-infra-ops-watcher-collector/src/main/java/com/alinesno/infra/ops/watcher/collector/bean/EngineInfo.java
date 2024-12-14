package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

@Data
public class EngineInfo {
    /**
     * 浏览器引擎名称。
     */
    private String name;

    /**
     * 浏览器引擎版本号。
     */
    private String version;
}