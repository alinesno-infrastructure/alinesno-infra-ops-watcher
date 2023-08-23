package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 告警信息实体类
 * 用于表示系统中的告警信息，包括告警来源、告警级别、告警描述和告警时间戳等信息。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@TableName("alert_messages")
public class AlertMessageEntity extends InfraBaseEntity {

    /**
     * 告警来源
     */
    @TableField(value = "source")
    private String source;

    /**
     * 告警级别
     */
    @TableField(value = "level")
    private String level;

    /**
     * 告警描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 告警时间戳
     */
    @TableField(value = "timestamp")
    private LocalDateTime timestamp;

    // 省略getter和setter方法
}
