package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.entity.ReportEntity;

/**
 * 报表Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IReportService extends IBaseService<ReportEntity> {

    /**
     * 获取每日报表
     *
     * @return 每日报表实体对象
     */
    ReportEntity getDailyReport();

    /**
     * 获取每周报表
     *
     * @return 每周报表实体对象
     */
    ReportEntity getWeeklyReport();

    /**
     * 获取每月报表
     *
     * @return 每月报表实体对象
     */
    ReportEntity getMonthlyReport();
}
