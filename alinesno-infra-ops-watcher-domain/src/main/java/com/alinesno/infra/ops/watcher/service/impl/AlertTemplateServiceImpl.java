package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.AlertTemplateEntity;
import com.alinesno.infra.ops.watcher.mapper.AlertTemplateMapper;
import com.alinesno.infra.ops.watcher.service.IAlertTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertTemplateServiceImpl extends IBaseServiceImpl<AlertTemplateEntity, AlertTemplateMapper> implements IAlertTemplateService {


    @Override
    public void initAlertTemplate(long userId) {

    }
}
