package com.alinesno.infra.ops.watcher.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Excel(name="祖级列表")
    @TableField("ancestors")
    @ColumnType(length=256)
    @ColumnComment("祖级列表")
    private String ancestors;

    @Excel(name="父类ID")
    @TableField("parent_id")
    @ColumnType(length=32)
    @ColumnComment("父类ID")
    private Long parentId;

    @TableField(value = "group_desc")
    @ColumnType(length=256)
    @ColumnComment("分组描述")
    private String groupDesc;

    @TableField(value = "group_type")
    @ColumnType(length=20)
    @ColumnComment("分组类型")
    private String groupType;

    @TableField(value = "order_num")
    @ColumnType(length=11)
    @ColumnComment("分组排序")
    private String orderNum;

    /** 子类型 */
    @TableField(exist = false)
    private List<GroupEntity> children = new ArrayList<>();

}
