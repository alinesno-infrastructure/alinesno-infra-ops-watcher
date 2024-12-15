package com.alinesno.infra.ops.watcher.collector.service;

import java.util.List;
import java.util.Map;

/**
 * 表示 MetricsHistogram 的 Service 接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IMetricsHistogramService {

    /**
     * 保存直方图信息
     * @param logList 日志列表
     */
    void saveHistogram(List<Map<String, Object>> logList);
}
