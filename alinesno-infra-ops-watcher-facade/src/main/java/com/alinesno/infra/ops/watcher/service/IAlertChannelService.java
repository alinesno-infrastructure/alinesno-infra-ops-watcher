package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.entity.AlertChannelEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LuoXiaoDong
 * @version 1.0.0
 */

public interface IAlertChannelService extends IBaseService<AlertChannelEntity> {

    /**
     * 判断是否打开此类型
     * @param suffix
     * @return
     */
    boolean isOpenType(String suffix);

    /**
     * 初始化文档结构
     * @param userId
     */
    void initAlertChannel(long userId);

}
