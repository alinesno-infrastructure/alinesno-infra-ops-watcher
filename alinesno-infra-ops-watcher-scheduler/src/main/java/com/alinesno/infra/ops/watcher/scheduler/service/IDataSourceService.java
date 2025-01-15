package com.alinesno.infra.ops.watcher.scheduler.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.scheduler.bean.CheckDbConnectResult;
import com.alinesno.infra.ops.watcher.scheduler.entity.DataSourceEntity;

public interface IDataSourceService extends IBaseService<DataSourceEntity> {

    /**
     * 数据库连接校验
     * @param dbListEntity
     * @return
     */
    CheckDbConnectResult checkDbConnect(DataSourceEntity dbListEntity);

    /**
     * 通过数据库ID获取数据源
     * @param dataSourceId
     * @return
     */
    DruidDataSource getDataSource(long dataSourceId);

}