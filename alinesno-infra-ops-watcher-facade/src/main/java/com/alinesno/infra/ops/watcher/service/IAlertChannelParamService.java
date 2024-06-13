package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.dto.AlertChannelParamDto;
import com.alinesno.infra.ops.watcher.entity.AlertChannelParamEntity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LuoXiaoDong
 * @version 1.0.0
 */
/**
 * 报警渠道参数服务接口，继承自IBaseService，专门用于报警渠道参数的增删改查操作。
 * 该接口主要负责管理报警渠道的参数设置，提供更新报警渠道参数的方法。
 */
public interface IAlertChannelParamService extends IBaseService<AlertChannelParamEntity> {

}
