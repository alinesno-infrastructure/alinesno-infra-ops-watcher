package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.ops.watcher.entity.AlertChannelEntity;
import com.alinesno.infra.ops.watcher.enums.AlertChannelEnum;
import com.alinesno.infra.ops.watcher.mapper.AlertChannelMapper;
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
public class AlertChannelServiceImpl extends IBaseServiceImpl<AlertChannelEntity, AlertChannelMapper> implements IAlertChannelService {

    @Override
    public boolean isOpenType(String suffix) {

        LambdaQueryWrapper<AlertChannelEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(AlertChannelEntity::getIsOpen , 1)
                .eq(AlertChannelEntity::getChannelName, suffix.toUpperCase()) ;

        long count = count(wrapper) ;

        return count > 0 ;
    }

    @Override
    public void initAlertChannel(long userId) {

        List<AlertChannelEntity> documentTypes = new ArrayList<>();

        for(AlertChannelEnum type : AlertChannelEnum.getAllChannels()){

            AlertChannelEntity typeE = new AlertChannelEntity(
                    type.getIcon(),
                    type.getCode(),
                    type.getLabel(),
                    type.getDesc(), true, 100, false) ;

            typeE.setOperatorId(userId);
            documentTypes.add(typeE) ;
        }

        this.remove(new LambdaQueryWrapper<AlertChannelEntity>()
                .eq(AlertChannelEntity::getOperatorId, userId)
                .in(AlertChannelEntity::getChannelName , AlertChannelEnum.getAllNames())) ;

        saveBatch(documentTypes) ;
    }
}
