package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.RuleEntity;
import com.alinesno.infra.ops.watcher.mapper.RuleMapper;
import com.alinesno.infra.ops.watcher.service.IRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleServiceImpl extends IBaseServiceImpl<RuleEntity, RuleMapper> implements IRuleService {


    @Override
    public void initRule(long userId) {

    }
}
