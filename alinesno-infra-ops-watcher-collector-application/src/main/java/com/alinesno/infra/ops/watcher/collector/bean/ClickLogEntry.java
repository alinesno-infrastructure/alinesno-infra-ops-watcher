package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

// 假设 ClickLogEntry 类已经被定义，它应该包含所有描述点击事件所需的属性。
@Data
public class ClickLogEntry {
    private String logType;  // 日志类型，如 "click"
    private String eleType; // 元素类型，如 "li"
    private String eleContent; // 元素内容
    private long timeStep; // 时间戳
    private String href; // 链接地址
    private String eleLocation; // 元素位置
}