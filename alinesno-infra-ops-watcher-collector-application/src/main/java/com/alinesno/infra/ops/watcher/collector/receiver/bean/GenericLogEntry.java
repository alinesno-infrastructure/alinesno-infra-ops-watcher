package com.alinesno.infra.ops.watcher.collector.receiver.bean;

import lombok.Data;

// 定义通用日志条目类
@Data
public class GenericLogEntry {
    private long timestamp;
    private String source; // 日志来源
    private String message; // 原始日志消息
}