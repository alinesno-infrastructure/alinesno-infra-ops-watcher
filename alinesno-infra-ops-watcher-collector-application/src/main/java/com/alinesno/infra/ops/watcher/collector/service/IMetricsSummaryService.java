package com.alinesno.infra.ops.watcher.collector.service;

import java.util.List;
import java.util.Map;

/**
 * 表示 MetricsSummary 的 Service 接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IMetricsSummaryService {

    /**
     * 保存 Summary 信息
     * @param logList 日志列表
     */
    void saveSummary(List<Map<String, Object>> logList);
}
