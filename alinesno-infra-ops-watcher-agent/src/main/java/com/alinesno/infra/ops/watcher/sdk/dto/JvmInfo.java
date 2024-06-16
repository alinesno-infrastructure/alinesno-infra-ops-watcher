package com.alinesno.infra.ops.watcher.sdk.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class JvmInfo {

    private String jvmName; // JVM名称
    private String jvmSpecName; // JVM规范名称
    private String jvmJavaVersion; // JVM JAVA版本
    private String jvmStartTime; // Java虚拟机的启动时间
    private String jvmUptime; // Java虚拟机的正常运行时间
    private String compilationName; // 编译器名称
    private String compilationTotalTime; // 编译器耗时
    private long totalThreadCount; // 总线程数(守护+非守护)
    private int daemonThreadCount; // 守护进程线程数
    private int peakThreadCount; // 峰值线程数
    private long totalStartedThreadCount; // Java虚拟机启动后创建并启动的线程总数
    private String osName; // 操作系统名称-版本号
    private String osArch; // 操作系统内核
    private int availableProcessors; // 可用的处理器数量
    private double systemLoadAverage; // 系统负载平均值
    private int currentLoadedClassCount; // 当前加载类数量
    private long unloadedClassCount; // 未加载类数量
    private long totalLoadedClassCount; // 总加载类数量
    private String nonHeapMemoryInfo; // 非堆内存信息
    private String heapMemoryInfo; // 堆内存信息

}
