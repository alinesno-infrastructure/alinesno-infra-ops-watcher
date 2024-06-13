package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.dto.AlertChannelParamDto;
import com.alinesno.infra.ops.watcher.entity.AlertChannelEntity;
import com.alinesno.infra.ops.watcher.entity.AlertChannelParamEntity;
import com.alinesno.infra.ops.watcher.enums.AlertChannelEnum;
import com.alinesno.infra.ops.watcher.mapper.AlertChannelMapper;
import com.alinesno.infra.ops.watcher.mapper.AlertChannelParamMapper;
import com.alinesno.infra.ops.watcher.service.IAlertChannelParamService;
import com.alinesno.infra.ops.watcher.service.IAlertChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LuoXiaoDong
 * @version 1.0.0
 */
@Slf4j
@Service
public class AlertChannelParamServiceImpl extends IBaseServiceImpl<AlertChannelParamEntity, AlertChannelParamMapper> implements IAlertChannelParamService {

}
