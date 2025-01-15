package com.alinesno.infra.ops.watcher.executor.handle;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.ops.watcher.executor.AbstractExecutorService;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;

@Slf4j
@Service("pythonExecutor")
public class PythonExecutor extends AbstractExecutorService {

    @SneakyThrows
    @Override
    public void execute(TaskInfoBean taskInfo) {
        configTaskParams(taskInfo, this);

        log.debug("PythonExecutor execute");

        String rawScript = readerRawScript() ;

        log.debug("rawScript: {}", rawScript) ;

        // 将python脚本写到临时文件
        File pythonFile = new File(getWorkspace(), "python_" + IdUtil.getSnowflakeNextIdStr() + ".py") ;
        FileUtils.writeStringToFile(pythonFile, rawScript  , Charset.defaultCharset() , false);

        runCommand("python3 " + pythonFile.getAbsolutePath());

    }
}
