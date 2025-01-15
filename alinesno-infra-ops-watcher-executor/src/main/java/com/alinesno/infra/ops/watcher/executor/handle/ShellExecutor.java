package com.alinesno.infra.ops.watcher.executor.handle;

import com.alinesno.infra.ops.watcher.executor.AbstractExecutorService;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("shellExecutor")
public class ShellExecutor extends AbstractExecutorService {

    @SneakyThrows
    @Override
    public void execute(TaskInfoBean taskInfo) {

        configTaskParams(taskInfo, this);
        log.debug("taskInfo = {}" , taskInfo.getTask());
        String rawScript = readerRawScript() ;

        log.debug("Shell Executor rawScript: {}", rawScript) ;

        // 构建多行命令行
        String command = """
                cd %s
                %s
                """.formatted(getWorkspace() , rawScript);

        writeLog("执行Shell:" +command);
        runCommand(command);
    }

}