package com.alinesno.infra.ops.watcher.constants;

public interface PipeConstants {

    String PROCESS_ID = "processId" ;

    String TRIGGER_GROUP_NAME = "quartz_scheduler_trigger"; // 触发器组名称
    String JOB_GROUP_NAME = "quartz_job"; // 任务组名称

    String RUNNING_LOGGER = "running.log" ;
}
