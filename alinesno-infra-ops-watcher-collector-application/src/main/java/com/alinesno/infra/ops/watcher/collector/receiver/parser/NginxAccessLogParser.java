package com.alinesno.infra.ops.watcher.collector.receiver.parser;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.receiver.LogParser;
import com.alinesno.infra.ops.watcher.collector.receiver.bean.FilebeatLogEntry;
import com.alinesno.infra.ops.watcher.collector.receiver.bean.NginxAccessLog;
import com.alinesno.infra.ops.watcher.collector.service.INginxAccessLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Nginx访问日志解析器
 */
@Slf4j
public class NginxAccessLogParser implements LogParser<NginxAccessLog> {

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

        NginxAccessLog nginxLog = JSONObject.parseObject(logMessage, NginxAccessLog.class);
        log.debug("Parsed Nginx Access Log: {}", nginxLog) ;

        INginxAccessLogService nginxAccessLogService = SpringUtil.getBean(INginxAccessLogService.class) ;
        nginxAccessLogService.saveNginxAccessLogs(nginxLog);
    }

}