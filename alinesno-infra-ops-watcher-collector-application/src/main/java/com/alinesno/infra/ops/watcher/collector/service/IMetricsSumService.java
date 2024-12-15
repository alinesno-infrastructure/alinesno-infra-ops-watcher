package com.alinesno.infra.ops.watcher.collector.service;

import java.util.List;
import java.util.Map;

/**
 * 表示 MetricsSum 的 Service 接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IMetricsSumService {

    /**
     * 保存 Sum 信息
     * @param logList 日志列表
     */
    void saveSum(List<Map<String, Object>> logList);
}
