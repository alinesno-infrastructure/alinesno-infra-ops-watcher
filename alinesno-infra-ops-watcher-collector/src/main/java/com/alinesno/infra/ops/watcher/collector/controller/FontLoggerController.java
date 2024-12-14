package com.alinesno.infra.ops.watcher.collector.controller;

import com.alinesno.infra.ops.watcher.collector.bean.LogRequestDto;
import com.alinesno.infra.ops.watcher.collector.service.LogHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class FontLoggerController {

    @Autowired
    private LogHandlerService logHandlerService;

    /**
     * 接收前端日志采集请求。
     *
     * @param logRequestDto 包含日志信息的数据传输对象。
     * @return 返回处理结果。
     */
    @PostMapping("/collect")
    public ResponseEntity<String> collectLogs(@RequestBody LogRequestDto logRequestDto) {
        // Pass the received DTO to the handler service for processing.
        logHandlerService.handleLog(logRequestDto);

        // Return success response or handle errors as appropriate.
        return ResponseEntity.ok("Logs collected successfully.");
    }
}