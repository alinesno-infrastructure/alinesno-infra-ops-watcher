package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.dto.AlertChannelParamDto;
import com.alinesno.infra.ops.watcher.entity.AlertChannelEntity;
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


    /**
     * 批量更新报警渠道参数。
     * 该方法接收一个AlertChannelParamEntity的列表，用于更新已存在的报警渠道参数。
     * 更新操作可能是部分更新，只对列表中指定的属性进行修改。
     *
     * @param paramDtos 报警渠道参数的实体类列表，包含待更新的参数信息。
     * @param channelId 所属渠道
     */
    void updateAlertChannelParams(List<AlertChannelParamEntity> paramDtos , long channelId);
}
