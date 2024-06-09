package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.entity.GroupEntity;

/**
 * 告警信息Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IGroupService extends IBaseService<GroupEntity> {

    /**
     * 初始化分组信息
     * @param userId
     */
    void initGroup(long userId);

}
