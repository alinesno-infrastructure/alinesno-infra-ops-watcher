package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 通知分组
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("alert_group")
@Data
public class GroupEntity extends InfraBaseEntity {

    @TableField(value = "group_name")
    @ColumnType(length=32)
    @ColumnComment("分组名称")
    private String groupName;

    @TableField(value = "group_desc")
    @ColumnType(length=256)
    @ColumnComment("分组描述")
    private String groupDesc;

    @TableField(value = "group_type")
    @ColumnType(length=20)
    @ColumnComment("分组类型")
    private String groupType;


}
