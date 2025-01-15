package com.alinesno.infra.ops.watcher.metrics.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.metrics.entity.DataMetricEntity;
import com.alinesno.infra.ops.watcher.metrics.mapper.DataMetricMapper;
import com.alinesno.infra.ops.watcher.metrics.service.IDataMetricService;
import org.springframework.stereotype.Service;

@Service
public class DataMetricServiceImpl extends IBaseServiceImpl<DataMetricEntity, DataMetricMapper> implements IDataMetricService {
}