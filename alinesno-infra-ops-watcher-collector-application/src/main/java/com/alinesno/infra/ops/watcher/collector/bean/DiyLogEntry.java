package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

// 定义 DiyLogEntry 类以包含自定义日志条目的属性
@Data
public class DiyLogEntry {
    // 根据实际需求定义属性
    // 示例：
    private String logType;  // 日志类型
    private String content;  // 自定义内容
    private long timestamp;  // 时间戳
    // 可以根据实际需要添加更多字段
}