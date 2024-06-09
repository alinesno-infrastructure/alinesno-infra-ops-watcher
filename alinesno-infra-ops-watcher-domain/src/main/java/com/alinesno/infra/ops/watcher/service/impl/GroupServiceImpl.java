package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.GroupEntity;
import com.alinesno.infra.ops.watcher.mapper.GroupMapper;
import com.alinesno.infra.ops.watcher.service.IGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GroupServiceImpl extends IBaseServiceImpl<GroupEntity, GroupMapper> implements IGroupService {


    @Override
    public void initGroup(long userId) {

    }
}
