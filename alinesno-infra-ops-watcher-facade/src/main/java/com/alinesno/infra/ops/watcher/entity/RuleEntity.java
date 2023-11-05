package com.alinesno.infra.ops.watcher.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import lombok.Data;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 规则实体类
 * 用于表示系统中的规则信息，包括规则条件和规则动作等信息。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@TableName("rules")
@Data
public class RuleEntity extends InfraBaseEntity {

    /**
     * 规则条件
     */
    @TableField(value = "condition")
	@ColumnType(length=255)
	@ColumnComment("规则条件")
    private String condition;

    /**
     * 规则动作
     */
    @TableField(value = "action")
	@ColumnType(length=255)
	@ColumnComment("规则动作")
    private String action;
}
