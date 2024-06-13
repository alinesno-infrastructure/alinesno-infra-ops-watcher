package com.alinesno.infra.ops.watcher.sdk.utils;

import com.alinesno.infra.ops.watcher.sdk.dto.Env;

/**
 * 环境工具类，用于获取和封装当前运行环境的相关信息。
 */
public class EnvironmentUtil {

    /**
     * 获取当前环境的详细信息。
     *
     * 通过系统环境变量和系统属性获取主机IP、主机名、JDK版本、操作系统信息、JavaHome路径、日志路径和应用名称，
     * 并封装到Env对象中返回。
     *
     * @return Env 对象，包含当前环境的详细信息。
     */
    public static Env getEnvironmentDetails() {

        // 创建Env对象用于存储环境信息
        Env env = new Env();
        // 从系统环境变量中获取主机IP，并设置到Env对象中
        env.setIp(System.getenv("HOST_IP"));
        // 从系统环境变量中获取主机名，并设置到Env对象中
        env.setHostname(System.getenv("HOSTNAME"));
        // 从系统属性中获取JDK版本，并设置到Env对象中
        env.setJdk(System.getProperty("java.version"));
        // 从系统属性中获取操作系统名称和版本号，合并后设置到Env对象中
        env.setOsInfo(System.getProperty("os.name") + " " + System.getProperty("os.version"));
        // 从系统属性中获取Java版本，并设置到Env对象中
        env.setJavaVersion(System.getProperty("java.version"));
        // 从系统属性中获取Java安装路径，并设置到Env对象中
        env.setJavaHome(System.getProperty("java.home"));
        // 从系统环境变量中获取日志路径，并设置到Env对象中
        env.setLogPath(System.getenv("LOG_PATH"));
        // 从系统环境变量中获取应用名称，并设置到Env对象中
        env.setApplicationName(System.getenv("APPLICATION_NAME"));

        // 返回包含环境信息的Env对象
        return env;
    }

}

