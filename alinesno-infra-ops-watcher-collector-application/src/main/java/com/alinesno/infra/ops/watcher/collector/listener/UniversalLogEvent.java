package com.alinesno.infra.ops.watcher.collector.listener;

import com.alinesno.infra.ops.watcher.collector.bean.UniversalLogEntry;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UniversalLogEvent extends ApplicationEvent {

    private final UniversalLogEntry logEntry;

    public UniversalLogEvent(Object source, UniversalLogEntry logEntry) {
        super(source);
        this.logEntry = logEntry;
    }

}