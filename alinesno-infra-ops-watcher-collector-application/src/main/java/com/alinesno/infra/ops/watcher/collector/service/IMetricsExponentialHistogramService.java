package com.alinesno.infra.ops.watcher.collector.service;

import java.util.List;
import java.util.Map;

/**
 * 表示 MetricsExponentialHistogram 的 Service 接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IMetricsExponentialHistogramService {

    /**
     * 保存指数直方图信息
     * @param logList 日志列表
     */
    void saveHistogram(List<Map<String, Object>> logList);
}
