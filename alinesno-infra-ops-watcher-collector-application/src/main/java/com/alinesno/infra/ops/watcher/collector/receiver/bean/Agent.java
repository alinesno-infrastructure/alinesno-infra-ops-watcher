package com.alinesno.infra.ops.watcher.collector.receiver.bean;

import lombok.Data;

@Data
public class Agent {
    private String ephemeralId;
    private String id;
    private String name;
    private String type;
    private String version;
}

