package com.alinesno.infra.ops.watcher.collector.service;


import com.alinesno.infra.ops.watcher.collector.bean.LogRequestDto;

public interface LogHandlerService {
    /**
     * 处理从前端收集的日志信息。
     *
     * @param logRequestDto 包含日志信息的数据传输对象。
     */
    void handleLog(LogRequestDto logRequestDto);
}