package com.alinesno.infra.ops.watcher.collector.receiver;

import com.alinesno.infra.ops.watcher.collector.receiver.bean.FilebeatLogEntry;

// 定义 LogParser 接口
public interface LogParser<T> {
    void setFilebeatLog(FilebeatLogEntry filebeatLog);
    void parse(String logMessage) throws IllegalArgumentException;
}