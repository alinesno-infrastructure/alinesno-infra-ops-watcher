package com.alinesno.infra.ops.watcher.executor.handle;

import com.alinesno.infra.ops.watcher.executor.AbstractExecutorService;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("startFlowExecutor")
public class StartFlowExecutor extends AbstractExecutorService {

    @Override
    public void execute(TaskInfoBean taskInfo) {
        log.debug("StartFlowExecutor execute");

        configTaskParams(taskInfo, this);

        Map<String , String> globalMap = this.getGlobalEnv() ;

        // 打印全局变量
        globalMap.forEach((k, v) -> writeLog("--->>> GlobalEnv: " + k + " = " + v));

    }
}
