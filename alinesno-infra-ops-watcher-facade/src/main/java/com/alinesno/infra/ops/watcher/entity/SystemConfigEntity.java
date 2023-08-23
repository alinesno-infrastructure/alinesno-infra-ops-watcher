package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 系统配置实体类
 * 用于表示系统的配置信息，包括数据库连接信息和日志级别等。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@TableName("system_config")
public class SystemConfigEntity extends InfraBaseEntity {

    /**
     * 日志级别
     */
    @TableField(value = "log_level")
    private String logLevel;

    // 省略getter和setter方法
}
