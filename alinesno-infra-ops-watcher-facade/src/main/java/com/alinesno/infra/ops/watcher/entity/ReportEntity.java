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
 * 报表实体类
 * 用于表示系统中的报表信息，包括报表关联的用户、报表类型、报表日期和报表内容等信息。
 */
@EqualsAndHashCode(callSuper = true)
@TableName("reports")
@Data
public class ReportEntity extends InfraBaseEntity {

    /**
     * 关联的用户ID
     */
    @TableField(value = "user_id")
	@ColumnType(length=50)
	@ColumnComment("关联的用户ID")
    private Long userId;

    /**
     * 报表类型
     */
    @TableField(value = "report_type")
	@ColumnType(length=20)
	@ColumnComment("报表类型")
    private String reportType;

    /**
     * 报表日期
     */
    @TableField(value = "report_date")
	@ColumnType(length=10)
	@ColumnComment("报表日期")
    private Date reportDate;

    /**
     * 报表内容
     */
    @TableField(value = "report_content")
	@ColumnType(length=255)
	@ColumnComment("报表内容")
    private String reportContent;
}
