package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.mapper.AlertMessageMapper;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 告警信息Service业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Service
public class AlertMessageServiceImpl extends IBaseServiceImpl<AlertMessageEntity, AlertMessageMapper> implements IAlertMessageService {
    // 日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(AlertMessageServiceImpl.class);

    @Override
    public void processAlertMessage(AlertMessageEntity alertMessage) {

    }
}
