package com.alinesno.infra.ops.watcher.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 告警信息实体类
 * 用于表示系统中的告警信息，包括告警来源、告警级别、告警描述和告警时间戳等信息。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("alert_messages")
@Data
public class AlertMessageEntity extends InfraBaseEntity {

    /**
     * 告警来源
     */
    @TableField(value = "source")
	@ColumnType(length=255)
	@ColumnComment("告警来源")
    private String source;

    /**
     * 告警级别
     */
    @TableField(value = "level")
	@ColumnType(length=1)
	@ColumnComment("告警级别")
    private String level;

    private String category; // 预警信息分类
    private String status; // 预警信息状态

    /**
     * 告警描述
     */
    @TableField(value = "description")
	@ColumnType(length=255)
	@ColumnComment("告警描述")
    private String description;

    /**
     * 告警时间戳
     */
    @TableField(value = "timestamp")
	@ColumnType(length=20)
	@ColumnComment("告警时间戳")
    private LocalDateTime timestamp;
}
