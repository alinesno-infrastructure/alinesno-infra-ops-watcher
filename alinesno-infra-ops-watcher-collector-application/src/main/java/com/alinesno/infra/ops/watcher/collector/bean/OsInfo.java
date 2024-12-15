package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

@Data
public class OsInfo {
    /**
     * 操作系统名称。
     */
    private String name;

    /**
     * 操作系统版本号。
     */
    private String version;
}