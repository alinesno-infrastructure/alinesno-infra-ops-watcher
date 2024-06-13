package com.alinesno.infra.ops.watcher.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.dto.TreeSelectDto;
import com.alinesno.infra.ops.watcher.entity.GroupEntity;

import java.util.List;

/**
 * 告警信息Service接口
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
public interface IGroupService extends IBaseService<GroupEntity> {

    /**
     * 初始化分组信息
     * @param userId
     */
    void initGroup(long userId);

    /**
     * 查询类型列表树
     * @return
     */
    List<TreeSelectDto> selectCatalogTreeList();

}
