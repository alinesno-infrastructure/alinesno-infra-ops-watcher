package com.alinesno.infra.ops.watcher.collector.bean;

import lombok.Data;
import java.util.List;

@Data
public class LogRequestDto {

    /**
     * 点击消息列表。
     */
    private List<ClickLogEntry> click_list;

    /**
     * IO消息列表，继承自PerformanceEntry。
     */
    private List<IoLogEntry> io_list;

    /**
     * 自定义日志条目列表。
     */
    private List<DiyLogEntry> diy_list;

    /**
     * 终端信息。
     */
    private TerminalInfo terminal_info;

    /**
     * 性能信息。
     */
    private PerformanceInfo performance_info;

    /**
     * 用户唯一标识符，由IP与用户代理生成。
     */
    private String uid;

    /**
     * 日志配置中的ID标识。
     */
    private String id;
}