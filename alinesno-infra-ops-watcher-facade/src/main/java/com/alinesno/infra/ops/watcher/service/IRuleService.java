package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.entity.RuleEntity;

/**
 * 告警信息Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IRuleService extends IBaseService<RuleEntity> {

    /**
     * 初始化通知模板
     * @param userId
     */
    void initRule(long userId);

}
