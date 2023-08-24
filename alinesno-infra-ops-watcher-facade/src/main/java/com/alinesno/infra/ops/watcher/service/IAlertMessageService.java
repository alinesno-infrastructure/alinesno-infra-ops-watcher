package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;

/**
 * 告警信息Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IAlertMessageService extends IBaseService<AlertMessageEntity> {

    /**
     * 处理告警信息
     *
     * @param alertMessage 告警信息实体对象
     */
    void processAlertMessage(AlertMessageEntity alertMessage);
}
