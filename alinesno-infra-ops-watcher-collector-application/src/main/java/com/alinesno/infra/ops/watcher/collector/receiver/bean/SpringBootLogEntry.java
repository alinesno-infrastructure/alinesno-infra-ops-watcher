package com.alinesno.infra.ops.watcher.collector.receiver.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpringBootLogEntry extends GenericLogEntry {
    private String app;
    private String active;
    private String host;
    private String reqid;
    private String uid;
    private String time; // 时间戳将被转换为 long 类型
    private String level;
    private String pid;
    private String className; // 避免与关键字 'class' 冲突
    private String methodName;
    private String line;
    private String message;
    private String stackTrace;

    // 将时间字符串转换为毫秒数
    public void setTimeInMillis() {
        try {
            this.setTimestamp(java.time.ZonedDateTime.parse(this.time + "Z",
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX"))
                    .toInstant()
                    .toEpochMilli());
        } catch (Exception e) {
            // 处理解析失败的情况
            this.setTimestamp(0L);
        }
    }

    // 构造函数用于从JSON字符串初始化对象
    public static SpringBootLogEntry fromJson(String json) throws IllegalArgumentException {
        try {
            return new ObjectMapper().readValue(json, SpringBootLogEntry.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse JSON log entry: " + json, e);
        }
    }
}