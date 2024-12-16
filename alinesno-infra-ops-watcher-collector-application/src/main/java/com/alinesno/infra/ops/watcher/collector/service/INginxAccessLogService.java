package com.alinesno.infra.ops.watcher.collector.service;

import com.alinesno.infra.ops.watcher.collector.receiver.bean.NginxAccessLog;

import java.util.List;
import java.util.Map;

public interface INginxAccessLogService {
    /**
     * 批量保存Nginx访问日志记录到数据库。
     *
     * @param logList 包含多个日志条目的列表，每个条目是一个Map<String, Object>
     */
    void saveNginxAccessLogs(NginxAccessLog logList);
}