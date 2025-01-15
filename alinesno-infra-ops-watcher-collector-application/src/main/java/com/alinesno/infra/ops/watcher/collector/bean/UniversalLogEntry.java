package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UniversalLogEntry {

    private LocalDateTime timestamp;
    private String level;
    private String message;
    private Throwable exception;

}