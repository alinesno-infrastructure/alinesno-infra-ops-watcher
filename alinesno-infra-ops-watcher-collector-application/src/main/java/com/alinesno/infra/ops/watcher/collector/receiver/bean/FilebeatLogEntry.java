package com.alinesno.infra.ops.watcher.collector.receiver.bean;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data // Lombok annotation to generate getters, setters, equals, hashcode and toString
public class FilebeatLogEntry {
    private long timestamp; // 使用 long 类型存储时间戳
    private Metadata metadata;
    private Ecs ecs;
    private Host host;
    private Agent agent;
    private Log log;
    private String message;
    private Input input;
    private String logSource;

    // 如果您需要从原始字符串格式的时间戳创建对象，可以添加一个构造函数或静态方法来进行转换。
    @JsonCreator
    public FilebeatLogEntry(@JsonProperty("timestamp") String timestampStr, 
                            @JsonProperty("metadata") Metadata metadata,
                            @JsonProperty("ecs") Ecs ecs,
                            @JsonProperty("host") Host host,
                            @JsonProperty("agent") Agent agent,
                            @JsonProperty("log") Log log,
                            @JsonProperty("message") String message,
                            @JsonProperty("input") Input input,
                            @JsonProperty("log_source") String logSource) {
        this.metadata = metadata;
        this.ecs = ecs;
        this.host = host;
        this.agent = agent;
        this.log = log;
        this.message = message;
        this.input = input;
        this.logSource = logSource;
        // 将ISO 8601格式的时间戳字符串转换为毫秒数
        if (timestampStr != null && !timestampStr.isEmpty()) {
            this.timestamp = parseIso8601ToMillis(timestampStr);
        } else {
            this.timestamp = 0L;
        }
    }

    private static long parseIso8601ToMillis(String iso8601Timestamp) {
        try {
            // 假设输入的时间戳是ISO 8601格式，例如："2024-12-16T07:54:25.696Z"
            return java.time.Instant.parse(iso8601Timestamp).toEpochMilli();
        } catch (Exception e) {
            // 处理解析失败的情况
            return 0L;
        }
    }
}

// 其他类定义保持不变，除了将Date类型改为long类型的地方

