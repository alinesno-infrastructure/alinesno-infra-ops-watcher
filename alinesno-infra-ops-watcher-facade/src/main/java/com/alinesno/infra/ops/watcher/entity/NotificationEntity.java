package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * 通知实体类
 * 用于表示系统中的通知信息，包括通知关联的规则、通知接收者、通知方式和通知时间戳等信息。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@TableName("notifications")
public class NotificationEntity extends InfraBaseEntity {

    /**
     * 关联的规则ID
     */
    @TableField(value = "rule_id")
    private Integer ruleId;

    /**
     * 通知接收者
     */
    @TableField(value = "recipient")
    private String recipient;

    /**
     * 通知方式
     */
    @TableField(value = "method")
    private String method;

    /**
     * 通知时间戳
     */
    @TableField(value = "timestamp")
    private LocalDateTime timestamp;

    // 非数据库字段，关联规则信息
    @TableField(exist = false)
    private RuleEntity rule;

    // 省略getter和setter方法
}
