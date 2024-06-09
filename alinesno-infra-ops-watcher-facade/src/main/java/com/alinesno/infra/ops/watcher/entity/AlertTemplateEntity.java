package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体类
 * 用于表示系统的配置信息，包括数据库连接信息和日志级别等。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("alert_template")
@Data
public class AlertTemplateEntity extends InfraBaseEntity {

    /**
     * 日志级别
     */
    @TableField(value = "log_level")
	@ColumnType(length=10)
	@ColumnComment("日志级别")
    private String logLevel;
}
