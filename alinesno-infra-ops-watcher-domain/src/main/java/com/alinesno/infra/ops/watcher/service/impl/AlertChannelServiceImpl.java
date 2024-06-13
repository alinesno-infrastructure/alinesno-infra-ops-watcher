package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.dto.AlertChannelParamDto;
import com.alinesno.infra.ops.watcher.entity.AlertChannelEntity;
import com.alinesno.infra.ops.watcher.entity.AlertChannelParamEntity;
import com.alinesno.infra.ops.watcher.enums.AlertChannelEnum;
import com.alinesno.infra.ops.watcher.mapper.AlertChannelMapper;
import com.alinesno.infra.ops.watcher.service.IAlertChannelParamService;
import com.alinesno.infra.ops.watcher.service.IAlertChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AlertChannelServiceImpl extends IBaseServiceImpl<AlertChannelEntity, AlertChannelMapper> implements IAlertChannelService {

    @Autowired
    private IAlertChannelParamService alertChannelParamService ;

    /**
     * 检查是否开启特定类型的告警通道。
     *
     * 此方法用于确定系统中是否已经开启了一种特定后缀的告警通道。它通过查询数据库中对应后缀的告警通道的开启状态来实现。
     * 参数suffix指定告警通道的类型后缀，方法返回true如果该类型告警通道已开启，否则返回false。
     *
     * @param suffix 告警通道类型的后缀，用于查询数据库中对应的告警通道。
     * @return 如果存在开启的告警通道则返回true，否则返回false。
     */
    @Override
    public boolean isOpenType(String suffix) {
        // 创建Lambda查询包装器，用于构建数据库查询条件。
        LambdaQueryWrapper<AlertChannelEntity> wrapper = new LambdaQueryWrapper<>() ;
        // 设置查询条件：告警通道的开启状态为是，并且通道名称的后缀与传入的后缀匹配（不区分大小写）。
        wrapper.eq(AlertChannelEntity::getIsOpen , 1)
                .eq(AlertChannelEntity::getChannelName, suffix.toUpperCase()) ;

        // 根据查询条件查询满足条件的告警通道数量。
        long count = count(wrapper) ;

        // 判断满足条件的告警通道数量是否大于0，如果是则返回true，表示该类型告警通道已开启；否则返回false。
        return count > 0 ;
    }

    /**
     * 初始化用户的警报渠道。
     * 该方法为每个可用的警报渠道创建一个实体对象，并为指定用户设置这些渠道。
     * 首先，它清除用户之前设置的所有警报渠道，然后批量保存新的警报渠道设置。
     * 这确保了用户总是根据最新的可用渠道进行初始化，并且避免了重复渠道的问题。
     *
     * @param userId 用户ID，用于指定哪个用户的警报渠道将被初始化。
     */
    @Override
    public void initAlertChannel(long userId) {
        // 初始化一个空列表，用于存储待创建的警报渠道实体
        List<AlertChannelEntity> channelTypes = getAlertChannelEntities(userId);

        // 删除用户之前的所有警报渠道设置，为新的设置腾出空间
        this.remove(new LambdaQueryWrapper<AlertChannelEntity>()
                .eq(AlertChannelEntity::getOperatorId, userId)
                .in(AlertChannelEntity::getChannelName, AlertChannelEnum.getAllNames()));

        // 批量保存新的警报渠道设置
        saveBatch(channelTypes);

        // 进一步添加渠道参数
        List<AlertChannelParamEntity> paramEntities = new ArrayList<>() ;

        for(AlertChannelEntity e : channelTypes){
            List<AlertChannelParamDto> params = AlertChannelEnum.getChannelParams(e.getChannelCode());

            for(AlertChannelParamDto dto : params){
                AlertChannelParamEntity param = new AlertChannelParamEntity(e.getId(), dto.getParamKey(), dto.getParamValue(), dto.getParamDesc(), dto.isInner());
                paramEntities.add(param);
            }

        }

        alertChannelParamService.saveBatch(paramEntities);
    }

    /**
     * 根据用户ID获取警报渠道实体列表。
     * 此方法通过遍历所有可用的警报渠道类型，为每个类型创建一个警报渠道实体，并设置其初始状态。
     * 这些实体随后将与特定用户关联，为该用户提供可用的警报渠道选项。
     *
     * @param userId 用户ID，用于与警报渠道实体关联。
     * @return 包含所有警报渠道实体的列表。
     */
    private static List<AlertChannelEntity> getAlertChannelEntities(long userId) {
        // 初始化一个空的警报渠道实体列表
        List<AlertChannelEntity> channelTypes = new ArrayList<>();

        // 遍历所有可用的警报渠道类型
        // 遍历所有可用的警报渠道
        for(AlertChannelEnum type : AlertChannelEnum.getAllChannels()) {
            // 创建一个新的警报渠道实体，设置基础属性和默认值
            // 创建一个新的警报渠道实体，设置渠道的基本信息和默认值
            AlertChannelEntity typeE = new AlertChannelEntity(
                    type.getIcon(),
                    type.getCode(),
                    type.getLabel(),
                    type.getDesc(), true, 100, false);

            // 设置实体的操作者ID为传入的用户ID
            // 为实体设置操作者ID，即当前处理的用户ID
            typeE.setOperatorId(userId);
            // 将新创建的实体添加到列表中
            // 将实体添加到列表中，准备后续批量保存
            channelTypes.add(typeE);
        }
        // 返回包含所有警报渠道实体的列表
        return channelTypes;
    }

    /**
     * 更新警报渠道参数。
     * 此方法用于批量更新或插入警报渠道的参数设置。通过对传入的参数实体列表遍历，
     * 每个参数实体设置相应的警报渠道ID，然后保存或更新到数据库中。
     *
     * @param paramDtos 警报渠道参数实体列表，包含待更新的参数信息。
     * @param channelId 警报渠道ID，用于为每个参数实体设置警报渠道。
     */
    @Override
    public void updateAlertChannelParams(List<AlertChannelParamEntity> paramDtos , long channelId) {

        // 删除所有的渠道参数
        alertChannelParamService.remove(new LambdaQueryWrapper<AlertChannelParamEntity>()
                .eq(AlertChannelParamEntity::getAlertChannelId , channelId)) ;

        // 遍历参数实体列表
        for(AlertChannelParamEntity paramDto : paramDtos){
            // 设置警报渠道ID
            paramDto.setAlertChannelId(channelId);
            // 保存或更新参数实体到数据库
            alertChannelParamService.save(paramDto);
        }
    }

}
