package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alinesno.infra.ops.watcher.collector.bean.LogRequestDto;
import com.alinesno.infra.ops.watcher.collector.service.LogHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogHandlerServiceImpl implements LogHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(LogHandlerServiceImpl.class);

    /**
     * 处理从前端收集的日志信息。
     *
     * @param logRequestDto 包含日志信息的数据传输对象。
     */
    @Override
    public void handleLog(LogRequestDto logRequestDto) {
        // Here you can add your business logic for handling the logs.
        // For demonstration purposes, we're simply logging the incoming data.

        logger.info("Received log request with UID: {}", logRequestDto.getUid());

        // Example of processing IO entries:
        if (logRequestDto.getIoList() != null && !logRequestDto.getIoList().isEmpty()) {
            logRequestDto.getIoList().forEach(ioEntry -> {
                logger.info("Processing IO entry: {}", ioEntry.getName());
                // Add more detailed processing here as needed.
            });
        }

        // Additional processing steps...
        // Persist to database, send to message queue, etc.

        // Log terminal info
        logger.info("Terminal Info: {}", logRequestDto.getTerminalInfo());

        // Log performance info
        logger.info("Performance Info: {}", logRequestDto.getPerformanceInfo());

        // Log additional fields as necessary
        logger.info("Log ID: {}", logRequestDto.getId());

        // In a real-world scenario, this is where you would implement the actual log processing logic.
    }
}