package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 存储应用实体类
 * </p>
 * <p>
 * 该类继承自InfraBaseEntity，表示存储应用的基本信息。
 * </p>
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("project_channel")
@Data
public class ProjectChannelEntity extends InfraBaseEntity {

    @TableField("project_id")
    @ColumnType(length=32)
    @ColumnComment("项目名称")
    private long projectId ;

    @TableField("channel_code")
    @ColumnType(length=32)
    @ColumnComment("渠道代码")
    private String channelCode ;

    @TableField("channel_type")
    @ColumnType(length=32)
    @ColumnComment("渠道类型")
    private String channelType;

    @TableField("channel_desc")
    @ColumnType(length=256)
    @ColumnComment("渠道描述")
    private String channelDesc ;

    // >>>>>>>>>>>>>>>>> 数据统计_starter >>>>>>>>>>>>>>>>.
    @TableField("warning_count")
    @ColumnType(length = 11)
    @ColumnComment("警告数")
    private int warningCount = 0;

    @TableField("critical_count")
    @ColumnType(length = 11)
    @ColumnComment("紧急数")
    private int criticalCount = 0;

    @TableField("alert_count")
    @ColumnType(length = 11)
    @ColumnComment("告警数")
    private int alertCount = 0;
    // >>>>>>>>>>>>>>>>> 数据统计_end >>>>>>>>>>>>>>>>.

}
