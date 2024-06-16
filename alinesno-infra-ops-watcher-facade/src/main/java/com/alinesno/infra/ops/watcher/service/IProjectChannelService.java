package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.dto.ProjectChannelDto;
import com.alinesno.infra.ops.watcher.entity.ProjectChannelEntity;

/**
 * 告警信息Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IProjectChannelService extends IBaseService<ProjectChannelEntity> {

    /**
     * 初始化默认项目渠道
     *
     * @param userId
     * @param projectId
     */
    void initDefaultChannel(long userId, long projectId);

    /**
     * 添加项目渠道
     * @param dto
     */
    void addProviderChannel(ProjectChannelDto dto);

}
