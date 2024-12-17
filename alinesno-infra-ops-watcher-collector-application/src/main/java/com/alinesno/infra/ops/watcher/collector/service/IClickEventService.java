package com.alinesno.infra.ops.watcher.collector.service;

import com.alinesno.infra.ops.watcher.collector.bean.ClickLogEntry;

import java.util.List;

public interface IClickEventService {
    void saveClickEvents(List<ClickLogEntry> clickLog, String uid, String id);
}