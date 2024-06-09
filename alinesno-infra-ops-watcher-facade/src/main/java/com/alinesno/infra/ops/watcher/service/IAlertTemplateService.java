package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.entity.AlertTemplateEntity;

/**
 * 告警信息Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IAlertTemplateService extends IBaseService<AlertTemplateEntity> {

    /**
     * 初始化通知模板
     * @param userId
     */
    void initAlertTemplate(long userId);

}
