package com.alinesno.infra.ops.watcher.collector.receiver.bean;

import lombok.Data;

@Data
public class Log {
    private long offset;
    private FilePath file;
}