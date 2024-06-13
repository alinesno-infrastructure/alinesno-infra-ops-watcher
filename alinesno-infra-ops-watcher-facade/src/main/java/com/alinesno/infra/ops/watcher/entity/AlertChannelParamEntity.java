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
 * 消息通知方式实体
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("alert_channel_param")
@Data
public class AlertChannelParamEntity extends InfraBaseEntity {

    /**
     * 渠道ID
     * 用于标识消息通知渠道的唯一标识。
     */
    @TableField(value = "alert_channel_id")
    @ColumnType(length = 32)
    @ColumnComment("渠道ID")
    private long alertChannelId ;

    /**
     * 参数键
     * 用于唯一标识一个参数。
     */
    @TableField(value = "param_key")
    @ColumnType(length = 32)
    @ColumnComment("参数键")
    private String paramKey;

    /**
     * 参数值
     * 代表参数的具体取值。
     */
    @TableField(value = "param_value")
    @ColumnType(length = 64)
    @ColumnComment("参数值")
    private String paramValue;

    /**
     * 参数描述
     * 用于解释参数的作用或意义。
     */
    @TableField(value = "param_desc")
    @ColumnType(length = 256)
    @ColumnComment("参数描述")
    private String paramDesc;

    /**
     * 是否为内部参数
     * 标志参数是否仅用于系统内部，对外不可见。
     */
    @TableField(value = "is_inner")
    @ColumnType(length = 1)
    @ColumnComment("是否为内部参数")
    private boolean isInner;

    /**
     * 构造函数用于创建一个新的AlertChannelParamEntity实例。
     *
     * @param paramKey 参数的键，用于唯一标识一个参数。
     * @param paramValue 参数的值，对应于键的特定值。
     * @param paramDesc 参数的描述，解释参数的作用或用途。
     * @param isInner 标志位，表示参数是否为内部使用参数。
     */
    public AlertChannelParamEntity(long alertChannelId, String paramKey, String paramValue, String paramDesc, boolean isInner) {
        this.alertChannelId = alertChannelId;
        this.paramKey = paramKey;
        this.paramValue = paramValue;
        this.paramDesc = paramDesc;
        this.isInner = isInner;
    }
}
