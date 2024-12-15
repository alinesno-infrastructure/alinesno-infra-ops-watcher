package com.alinesno.infra.ops.watcher.collector.service;

import java.util.List;
import java.util.Map;

/**
 * 表示 Logs 的 Service 接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface ILogsService {

    /**
     * 保存日志信息
     * @param logList 日志列表
     */
    void saveLogs(List<Map<String , Object>> logList);
}
