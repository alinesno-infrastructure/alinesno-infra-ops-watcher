package com.alinesno.infra.ops.watcher.sdk.env;

import com.alinesno.infra.ops.watcher.sdk.dto.Env;
import com.alinesno.infra.ops.watcher.sdk.dto.JvmInfo;
import lombok.SneakyThrows;

import java.net.InetAddress;

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
    @SneakyThrows
    public static Env getEnvironmentDetails() {

        // 获取本地主机的InetAddress对象
        InetAddress localhost = InetAddress.getLocalHost();

        // 获取主机名
        String hostname = localhost.getHostName();

        // 获取主机的IP地址
        String ip = localhost.getHostAddress();

        // 创建Env对象用于存储环境信息
        Env env = new Env();

        env.setIp(ip);
        env.setHostname(hostname);

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

    /**
     * 获取JVM信息的静态方法。
     * 通过调用HeapMain类中的getJvmInfo方法，来获取当前Java虚拟机的详细信息。
     * 这包括但不限于JVM版本、内存配置、垃圾回收器信息等。
     *
     * @return JvmInfo 对象，包含当前JVM的详细信息。
     */
    public static JvmInfo getJvmInfo() {
        // 调用HeapMain类的getJvmInfo方法获取JVM信息
        return HeapMain.getJvmInfo() ;
    }
}

