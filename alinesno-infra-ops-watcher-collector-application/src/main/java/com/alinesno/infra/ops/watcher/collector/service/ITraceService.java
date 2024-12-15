package com.alinesno.infra.ops.watcher.collector.service;


import java.util.List;
import java.util.Map;

/**
 * 表示 trace 的 Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface ITraceService {

    /**
     * 保存链路跟踪信息
     * @param logList
     */
    void saveTrace(List<Map<String , Object>> logList);
}
