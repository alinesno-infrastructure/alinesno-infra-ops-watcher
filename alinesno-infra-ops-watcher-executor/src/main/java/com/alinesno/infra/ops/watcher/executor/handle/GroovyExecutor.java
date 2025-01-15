package com.alinesno.infra.ops.watcher.executor.handle;

import com.alibaba.druid.pool.DruidDataSource;
import com.alinesno.infra.ops.watcher.executor.AbstractExecutorService;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * K8S操作执行器
 */
@Slf4j
@Scope("prototype")
@Service("groovyExecutor")
public class GroovyExecutor extends AbstractExecutorService {

    @Override
    public void execute(TaskInfoBean task) {

        configTaskParams(task, this);

        log.debug("groovyExecutor execute");

        PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos)); //设置新的out


        String rawScript = readerRawScript() ;
        log.debug("rawScript: \r\n" + rawScript);

        // 创建 Binding 对象，用于绑定变量到 Groovy 脚本
        Binding binding = new Binding();

        binding.setVariable("taskInfo", task.getWorkspace());
        binding.setVariable("executorService", this);
        binding.setVariable("log", log);

        // 数据源配置
        DruidDataSource dataSource = getDataSource();
        if(dataSource != null){
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            binding.setVariable("dataSource", dataSource);
            binding.setVariable("jdbcTemplate", jdbcTemplate);
        }

        DruidDataSource sinkDataSource = getSinkDataSource() ;
        if(sinkDataSource != null){
            JdbcTemplate jdbcTemplate = new JdbcTemplate(sinkDataSource);
            binding.setVariable("sinkDataSource", sinkDataSource);
            binding.setVariable("sinkJdbcTemplate", jdbcTemplate);
        }

        // 创建 GroovyShell 实例
        GroovyShell shell = new GroovyShell(GroovyExecutor.class.getClassLoader(), binding);

        // 执行 Groovy 脚本
        shell.evaluate(rawScript) ;

        System.setOut(oldPrintStream); //恢复原来的System.out
        System.out.println(bos); //将bos中保存的信息输出,这就是我们上面准备要输出的内容

    }

}
