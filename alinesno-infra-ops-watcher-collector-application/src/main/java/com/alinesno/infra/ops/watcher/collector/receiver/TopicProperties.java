package com.alinesno.infra.ops.watcher.collector.receiver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "topic")
public class TopicProperties {
    private String prefix = "alinesno";
}