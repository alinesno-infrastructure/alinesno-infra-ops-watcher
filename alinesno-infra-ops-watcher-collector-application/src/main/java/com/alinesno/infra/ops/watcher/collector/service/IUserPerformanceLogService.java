package com.alinesno.infra.ops.watcher.collector.service;

import com.alinesno.infra.ops.watcher.collector.bean.PerformanceInfo;
import com.alinesno.infra.ops.watcher.collector.bean.TerminalInfo;

public interface IUserPerformanceLogService {
    void saveUserPerformanceLogs(TerminalInfo terminalInfo, PerformanceInfo performanceInfo, String uid, String id);
}