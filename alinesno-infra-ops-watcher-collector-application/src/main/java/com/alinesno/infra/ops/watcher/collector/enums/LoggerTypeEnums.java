package com.alinesno.infra.ops.watcher.collector.enums;

import lombok.Getter;

/**
 * 当前集成监控的日志类型
 */
@Getter
public enum LoggerTypeEnums {

    // 日志类型(nginx/springboot)
    NGINX("nginx" , "nginx日志"),
    SPRINGBOOT("springboot" , "springboot日志");

    private final String type ;
    private final String desc ;

    LoggerTypeEnums(String type , String desc) {
    	this.type = type ;
    	this.desc = desc ;
    }

}
