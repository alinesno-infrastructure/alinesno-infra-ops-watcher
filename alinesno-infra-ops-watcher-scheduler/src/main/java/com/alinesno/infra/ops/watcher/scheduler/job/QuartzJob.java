package com.alinesno.infra.ops.watcher.scheduler.job;

import com.alinesno.infra.common.core.context.SpringContext;
import com.alinesno.infra.ops.watcher.constants.PipeConstants;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import com.alinesno.infra.ops.watcher.scheduler.entity.EnvironmentEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.ProcessDefinitionEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.ResourceEntity;
import com.alinesno.infra.ops.watcher.scheduler.entity.TaskDefinitionEntity;
import com.alinesno.infra.ops.watcher.scheduler.service.IEnvironmentService;
import com.alinesno.infra.ops.watcher.scheduler.service.IProcessDefinitionService;
import com.alinesno.infra.ops.watcher.scheduler.service.IResourceService;
import com.alinesno.infra.ops.watcher.scheduler.service.ITaskDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

// 禁止并发执行
@Slf4j
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        Result result = getResult(context);

        // 运行任务实例
        TaskInfoBean task = new TaskInfoBean();
        task.setProcess(result.processDefinition());
        task.setEnvironment(result.environment());
        task.setResources(result.resourceList());

        result.processDefinitionService().runProcess(task, result.taskDefinitionList());
    }

    private static Result getResult(JobExecutionContext context) {
        IProcessDefinitionService processDefinitionService = SpringContext.getBean(IProcessDefinitionService.class);
        ITaskDefinitionService taskDefinitionService = SpringContext.getBean(ITaskDefinitionService.class) ;
        IEnvironmentService environmentService = SpringContext.getBean(IEnvironmentService.class) ;
        IResourceService resourceService = SpringContext.getBean(IResourceService.class) ;

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Long processId = jobDataMap.getLong(PipeConstants.PROCESS_ID);

        List<String> resourceIds = new ArrayList<>();

        ProcessDefinitionEntity processDefinition = processDefinitionService.getById(processId);
        List<TaskDefinitionEntity> taskDefinitionList = taskDefinitionService.lambdaQuery().eq(TaskDefinitionEntity::getProcessId, processId).list();
        EnvironmentEntity environment = null ;

        try{
            environment = environmentService.lambdaQuery().eq(EnvironmentEntity::getProcessId, processId).getEntity();
        }catch (Exception e){
            log.error("获取环境信息失败:{}", e.getMessage());
        }

        taskDefinitionList.forEach(taskDefinition -> resourceIds.add(taskDefinition.getResourceId()));
        List<ResourceEntity> resourceList = resourceService.lambdaQuery().in(ResourceEntity::getId, resourceIds).list();

        return new Result(processDefinitionService, processDefinition, taskDefinitionList, environment, resourceList);
    }

    private record Result(IProcessDefinitionService processDefinitionService,
                          ProcessDefinitionEntity processDefinition,
                          List<TaskDefinitionEntity> taskDefinitionList,
                          EnvironmentEntity environment,
                          List<ResourceEntity> resourceList) {
    }
}