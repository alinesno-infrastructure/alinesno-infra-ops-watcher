package com.alinesno.infra.ops.watcher.collector.service;

import java.util.List;
import java.util.Map;

public interface IUniversalLogsService {
    void saveLogs(List<Map<String, Object>> logList);
}