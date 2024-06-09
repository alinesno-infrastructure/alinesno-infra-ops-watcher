package com.alinesno.infra.ops.watcher.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 通知发送记录实体类
 * 用于记录已发送的通知信息，包括关联的用户、通知类型、通知内容和发送时间等信息。
 */
@EqualsAndHashCode(callSuper = true)
@TableName("alert_logs")
@Data
public class AlertLogEntity extends InfraBaseEntity {

    /**
     * 关联的用户ID
     */
    @TableField(value = "user_id")
	@ColumnType(length=50)
	@ColumnComment("关联的用户ID")
    private Long userId;

    /**
     * 通知类型
     */
    @TableField(value = "email_type")
	@ColumnType(length=50)
	@ColumnComment("通知类型")
    private String emailType;

    /**
     * 通知内容
     */
    @TableField(value = "email_content")
	@ColumnType(length=255)
	@ColumnComment("通知内容")
    private String emailContent;

    /**
     * 发送时间
     */
    @TableField(value = "sent_at")
	@ColumnType(length=19)
	@ColumnComment("发送时间")
    private Date sentAt;
}
