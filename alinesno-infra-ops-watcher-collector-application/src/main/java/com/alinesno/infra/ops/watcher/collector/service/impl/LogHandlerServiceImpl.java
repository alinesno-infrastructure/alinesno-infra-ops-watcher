package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alinesno.infra.ops.watcher.collector.bean.LogRequestDto;
import com.alinesno.infra.ops.watcher.collector.service.IClickEventService;
import com.alinesno.infra.ops.watcher.collector.service.IUserPerformanceLogService;
import com.alinesno.infra.ops.watcher.collector.service.LogHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogHandlerServiceImpl implements LogHandlerService {

    @Autowired
    private IUserPerformanceLogService userPerformanceLogService;

    @Autowired
    private IClickEventService clickEventService;

    /**
     * 处理从前端收集的日志信息。
     *
     * @param logRequestDto 包含日志信息的数据传输对象。
     */
    @Override
    public void handleLog(LogRequestDto logRequestDto) {
        // Here you can add your business logic for handling the logs.
        // For demonstration purposes, we're simply logging the incoming data.

        log.info("Received log request with UID: {}", logRequestDto.getUid());

        // Example of processing IO entries:
        if (logRequestDto.getIo_list() != null && !logRequestDto.getIo_list().isEmpty()) {
            logRequestDto.getIo_list().forEach(ioEntry -> {
                log.info("Processing IO entry: {}", ioEntry.getName());
                // Add more detailed processing here as needed.
            });
        }

        // Additional processing steps...
        // Persist to database, send to message queue, etc.

        // Log terminal info
        log.info("Terminal Info: {}", logRequestDto.getTerminal_info());

        // Log performance info
        log.info("Performance Info: {}", logRequestDto.getPerformance_info());

        // ClickList
        logRequestDto.getClick_list().forEach(clickEntry -> {
            log.info("Processing Click entry: {}", clickEntry);
            // Add more detailed processing here as needed.
        });

        // Log additional fields as necessary
        log.info("Log ID: {}", logRequestDto.getId());

        // In a real-world scenario, this is where you would implement the actual log processing logic.

        clickEventService.saveClickEvents(logRequestDto.getClick_list() ,
                logRequestDto.getUid(),
                logRequestDto.getId());

        userPerformanceLogService.saveUserPerformanceLogs(logRequestDto.getTerminal_info(),
                logRequestDto.getPerformance_info(),
                logRequestDto.getUid(),
                logRequestDto.getId());
    }
}