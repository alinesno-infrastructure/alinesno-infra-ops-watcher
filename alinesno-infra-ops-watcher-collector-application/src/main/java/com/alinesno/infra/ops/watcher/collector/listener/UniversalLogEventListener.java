package com.alinesno.infra.ops.watcher.collector.listener;

import com.alinesno.infra.ops.watcher.collector.bean.UniversalLogEntry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UniversalLogEventListener {

    @EventListener
    public void handleLogEvent(UniversalLogEvent event) {
        UniversalLogEntry logEntry = event.getLogEntry();
        // 这里可以添加处理日志对象的逻辑
        System.out.println("Logged [" + logEntry.getLevel() + "] at " + logEntry.getTimestamp() + ": " + logEntry.getMessage());
        
        if (logEntry.getException() != null) {
            logEntry.getException().printStackTrace();
        }
    }
}