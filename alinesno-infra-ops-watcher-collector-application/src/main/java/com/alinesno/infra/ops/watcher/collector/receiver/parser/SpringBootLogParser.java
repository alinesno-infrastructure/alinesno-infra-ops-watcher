package com.alinesno.infra.ops.watcher.collector.receiver.parser;

import com.alinesno.infra.ops.watcher.collector.receiver.LogParser;
import com.alinesno.infra.ops.watcher.collector.receiver.bean.FilebeatLogEntry;
import com.alinesno.infra.ops.watcher.collector.receiver.bean.SpringBootLogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SpringBootLogParser implements LogParser<SpringBootLogEntry> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private FilebeatLogEntry filebeatLog;

    @Override
    public void setFilebeatLog(FilebeatLogEntry filebeatLog) {
        this.filebeatLog = filebeatLog;
    }

    @Override
    public void parse(String logMessage) throws IllegalArgumentException {
        if (logMessage == null || logMessage.isEmpty()) {
            throw new IllegalArgumentException("Log message cannot be null or empty.");
        }

        try {
            SpringBootLogEntry log = objectMapper.readValue(logMessage, SpringBootLogEntry.class);
            log.setTimeInMillis(); // 将时间字符串转换为毫秒数
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse JSON log entry: " + logMessage, e);
        }
    }
}