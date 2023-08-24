package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 报表实体类
 * 用于表示系统中的报表信息，包括报表关联的用户、报表类型、报表日期和报表内容等信息。
 */
@TableName("reports")
public class ReportEntity extends InfraBaseEntity {

    /**
     * 关联的用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 报表类型
     */
    @TableField(value = "report_type")
    private String reportType;

    /**
     * 报表日期
     */
    @TableField(value = "report_date")
    private Date reportDate;

    /**
     * 报表内容
     */
    @TableField(value = "report_content")
    private String reportContent;

    // Getters and setters
}
