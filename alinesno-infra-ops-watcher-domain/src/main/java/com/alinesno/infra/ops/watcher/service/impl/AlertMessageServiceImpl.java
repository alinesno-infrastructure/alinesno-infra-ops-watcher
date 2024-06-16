package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.entity.ProjectChannelEntity;
import com.alinesno.infra.ops.watcher.mapper.AlertMessageMapper;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
import com.alinesno.infra.ops.watcher.service.IProjectChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 告警信息Service业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Service
public class AlertMessageServiceImpl extends IBaseServiceImpl<AlertMessageEntity, AlertMessageMapper> implements IAlertMessageService {

    @Autowired
    private IProjectChannelService projectChannelService ;

    @Override
    public void processAlertMessage(AlertMessageEntity alertMessage) {
        save(alertMessage);

        String category = alertMessage.getCategory();
        ProjectChannelEntity projectChannel = projectChannelService.getOne(new LambdaQueryWrapper<ProjectChannelEntity>()
                .eq(ProjectChannelEntity::getChannelCode, category)) ;

        projectChannel.setAlertCount(projectChannel.getAlertCount() + 1);

        int alertLevel = Integer.parseInt(alertMessage.getLevel());
        if(alertLevel > 2){
           projectChannel.setWarningCount(projectChannel.getWarningCount() + 1);
        }else {
           projectChannel.setCriticalCount(projectChannel.getCriticalCount() + 1);
        }

        projectChannelService.updateById(projectChannel);
    }
}
