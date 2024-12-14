package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;
import java.util.List;

@Data
public class LogRequestDto {
    /**
     * IO消息列表，继承自PerformanceEntry。
     */
    private List<IoLogEntry> ioList;

    /**
     * 终端信息。
     */
    private TerminalInfo terminalInfo;

    /**
     * 性能信息。
     */
    private PerformanceInfo performanceInfo;

    /**
     * 用户唯一标识符，由IP与用户代理生成。
     */
    private String uid;

    /**
     * 日志配置中的ID标识。
     */
    private String id;
}