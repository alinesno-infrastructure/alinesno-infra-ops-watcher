package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.ReportEntity;
import com.alinesno.infra.ops.watcher.mapper.ReportMapper;
import com.alinesno.infra.ops.watcher.service.IReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 报表Service业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Service
public class ReportServiceImpl extends IBaseServiceImpl<ReportEntity, ReportMapper> implements IReportService {
    // 日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public ReportEntity getDailyReport() {
        return null;
    }

    @Override
    public ReportEntity getWeeklyReport() {
        return null;
    }

    @Override
    public ReportEntity getMonthlyReport() {
        return null;
    }
}
