package com.alinesno.infra.ops.watcher.dto;

import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *告警消息数据传输对象。
 *
 * 该类继承自AlertMessageEntity，添加了项目名称和项目渠道名称的属性，
 * 用于在告警消息中提供更具体的上下文信息。
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AlertMessageDto extends AlertMessageEntity {

    /**
     * 项目名称，默认为空字符串。
     * 用于标识产生告警的项目，为空时需外部赋值。
     */
    private String projectName = "" ;

    /**
     * 项目渠道名称，默认为空字符串。
     * 代表告警发生的项目渠道，为空时需外部赋值。
     */
    private String projectChannelName = "" ;

}
