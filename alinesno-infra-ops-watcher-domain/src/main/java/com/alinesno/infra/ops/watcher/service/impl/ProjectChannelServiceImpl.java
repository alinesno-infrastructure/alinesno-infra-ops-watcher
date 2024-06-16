package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.dto.ProjectChannelDto;
import com.alinesno.infra.ops.watcher.entity.ProjectChannelEntity;
import com.alinesno.infra.ops.watcher.enums.ProviderChannelEnum;
import com.alinesno.infra.ops.watcher.mapper.ProjectChannelMapper;
import com.alinesno.infra.ops.watcher.service.IProjectChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import java.util.Arrays;

@Slf4j
@Service
public class ProjectChannelServiceImpl extends IBaseServiceImpl<ProjectChannelEntity, ProjectChannelMapper> implements IProjectChannelService {

    @Override
    public void initDefaultChannel(long userId, long projectId) {
        ProjectChannelEntity e = new ProjectChannelEntity() ;

        e.setProjectId(projectId);
        e.setOperatorId(userId);
        e.setChannelType(ProviderChannelEnum.SPRING_BOOT.getName());
        e.setChannelDesc("测试集成SpringBoot应用预警.");

        Sqids sqids = Sqids.builder().build();
        String code = sqids.encode(Arrays.asList(projectId, 3L));
        e.setChannelCode(code);

        save(e) ;
    }

    @Override
    public void addProviderChannel(ProjectChannelDto dto) {
        ProjectChannelEntity e = new ProjectChannelEntity() ;

        e.setProjectId(dto.getProjectId());
        e.setChannelType(dto.getChannelType());
        e.setChannelDesc(dto.getChannelDesc());

        Sqids sqids = Sqids.builder().build();
        String code = sqids.encode(Arrays.asList(dto.getProjectId() , System.currentTimeMillis() ,3L));
        e.setChannelCode(code);

        save(e);
    }
}
