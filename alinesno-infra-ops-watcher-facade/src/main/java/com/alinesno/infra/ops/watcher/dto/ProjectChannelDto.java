package com.alinesno.infra.ops.watcher.dto;

import lombok.Data;

@Data
public class ProjectChannelDto {

    private long projectId;
    private String channelType ;
    private String channelDesc ;
    private String channelCode ;

}
