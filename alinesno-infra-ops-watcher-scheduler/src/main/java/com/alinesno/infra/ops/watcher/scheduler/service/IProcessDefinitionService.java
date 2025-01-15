package com.alinesno.infra.ops.watcher.scheduler.service;

import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import com.alinesno.infra.ops.watcher.scheduler.dto.ProcessDefinitionDto;
import com.alinesno.infra.ops.watcher.scheduler.dto.ProcessTaskValidateDto;
import com.alinesno.infra.ops.watcher.scheduler.entity.ProcessDefinitionEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.TaskDefinitionEntity;


import java.util.List;

public interface IProcessDefinitionService extends IBaseService<ProcessDefinitionEntity> {

    /**
     * 运行任务实例
     * @param task
     * @param taskDefinitionList
     */
    void runProcess(TaskInfoBean task, List<TaskDefinitionEntity> taskDefinitionList);

    /**
     * 保存流程定义
     *
     * @param dto
     * @return
     */
    long commitProcessDefinition(ProcessDefinitionDto dto);

    /**
     * 运行验证任务
     * @param dto
     */
    void runProcessTask(ProcessTaskValidateDto dto);

    /**
     * 查询最近count条流程定义
     *
     * @param count
     * @param query
     * @param projectId
     * @return
     */
    List<ProcessDefinitionEntity> queryRecentlyProcess(int count, PermissionQuery query, long projectId);

    /**
     * 更新流程定义
     * @param dto
     */
    void updateProcessDefinition(ProcessDefinitionDto dto);

}
