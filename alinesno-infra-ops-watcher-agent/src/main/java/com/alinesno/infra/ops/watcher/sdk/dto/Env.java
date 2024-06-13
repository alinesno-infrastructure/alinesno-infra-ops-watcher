package com.alinesno.infra.ops.watcher.sdk.dto;

import lombok.Data;

/**
 * 这里是Java程序运行时的环境类，请列出出现异常的基本环境
 */
@Data
public class Env {

    private String ip ;
    private String hostname ;
    private String jdk ;
    private String osInfo ;
    private String javaVersion ;
    private String javaHome ;
    private String logPath ;
    private String applicationName ;

}
