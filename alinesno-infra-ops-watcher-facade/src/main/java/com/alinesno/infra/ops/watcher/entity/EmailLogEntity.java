package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 邮件发送记录实体类
 * 用于记录已发送的邮件信息，包括关联的用户、邮件类型、邮件内容和发送时间等信息。
 */
@TableName("email_logs")
public class EmailLogEntity extends InfraBaseEntity {

    /**
     * 关联的用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 邮件类型
     */
    @TableField(value = "email_type")
    private String emailType;

    /**
     * 邮件内容
     */
    @TableField(value = "email_content")
    private String emailContent;

    /**
     * 发送时间
     */
    @TableField(value = "sent_at")
    private Date sentAt;

    // Getters and setters
}
