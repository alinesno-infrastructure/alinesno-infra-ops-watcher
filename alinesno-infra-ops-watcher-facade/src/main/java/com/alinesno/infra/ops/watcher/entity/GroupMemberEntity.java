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
@TableName("alert_group_member")
@Data
public class GroupMemberEntity extends InfraBaseEntity {

    @TableField(value = "group_id")
    @ColumnType(length=32)
    @ColumnComment("分组ID")
    private long groupId;

    @TableField(value = "user_name")
    @ColumnType(length=32)
    @ColumnComment("用户名")
    private String userName;

    @TableField(value = "user_email")
    @ColumnType(length=32)
    @ColumnComment("用户邮箱")
    private String userEmail;

    @TableField(value = "user_mobile")
    @ColumnType(length=11)
    @ColumnComment("用户手机号")
    private String userMobile;

    @TableField(value = "user_role")
    @ColumnType(length=32)
    @ColumnComment("用户角色")
    private String userRole;

}
