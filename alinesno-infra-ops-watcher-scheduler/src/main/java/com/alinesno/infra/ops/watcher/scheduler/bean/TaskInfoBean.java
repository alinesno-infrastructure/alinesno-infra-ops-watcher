package com.alinesno.infra.ops.watcher.scheduler.bean;

import com.alinesno.infra.ops.watcher.scheduler.entity.EnvironmentEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.ProcessDefinitionEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.ResourceEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.TaskDefinitionEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 任务信息
 */
@ToString
@Data
public class TaskInfoBean implements Serializable {

    // 运行的工作空间
    private String workspace ;

    // 本地工程目录
    private String workspacePath ;

    // 环境定义
    private EnvironmentEntity environment ;

    // 任务定义
    private ProcessDefinitionEntity process ;

    // 任务列表
    private TaskDefinitionEntity task;

    // 资源列表
    private List<ResourceEntity> resources;

}
