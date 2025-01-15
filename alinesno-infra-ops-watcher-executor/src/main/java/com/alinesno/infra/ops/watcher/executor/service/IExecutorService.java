package com.alinesno.infra.ops.watcher.executor.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alinesno.infra.ops.watcher.scheduler.bean.TaskInfoBean;
import com.alinesno.infra.ops.watcher.scheduler.dto.ParamsDto;
import com.alinesno.infra.ops.watcher.scheduler.entity.EnvironmentEntity;

import java.util.List;
import java.util.Map;

/**
 * 任务执行器
 */
public interface IExecutorService {

    /**
     * 配置任务环境
     * @param taskInfo
     */
    void setTaskInfoBean(TaskInfoBean taskInfo) ;

    /**
     * 配置全局参数
     */
    void setGlobalEnv(Map<String , String> paramMap);

    /**
     * 配置参数
     */
    void setParams(ParamsDto config);

    /**
     * 配置空间
     */
    void setWorkspace(String workspace) ;

    /**
     * 配置读取数据库源
     */
    void setDataSource(DruidDataSource source) ;

    /**
     * 配置写入数据库源
     */
    void setSinkDataSource(DruidDataSource source) ;

    /**
     * 配置环境
     * @param environment
     */
    void setEnvironment(EnvironmentEntity environment);

    /**
     * 执行命令
     * @param command
     */
    void runCommand(String command);

    /**
     * 配置资源
     * @param resources
     */
    void setResource(List<String> resources) ;

    /**
     * 执行任务，用于job执行实例
     * @param task
     */
    void execute(TaskInfoBean task) ;

    /**
     * 设置密钥
     * @param secretMap
     */
    void setSecretMap(Map<String, String> secretMap) ;

    /**
     * 环境变量和处理，级别
     * env(最小) -> globalParams(次之) -> customParams(最大)
     *
     * @param environment
     * @param globalParams
     * @param customParams
     */
    void replaceGlobalParams(EnvironmentEntity environment,
                             String globalParams,
                             Map<String, String> customParams);

    /**
     * 关闭数据源
     */
    void closeDataSource();
}
