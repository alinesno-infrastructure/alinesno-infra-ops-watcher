package com.alinesno.infra.ops.watcher.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 告警通知模板实体类
 * 用于表示告警通知的模板信息，包括告警级别、内容模板、告警方式、接收人、生效时间等。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("alert_template")
@Data
public class AlertTemplateEntity extends InfraBaseEntity {

    /**
     * 告警级别
     */
    @TableField(value = "alert_level")
    @ColumnType(length = 10)
    @ColumnComment("告警级别")
    private String alertLevel;

    /**
     * 告警内容模板（英文）
     */
    @TableField(value = "alert_content_template_en")
    @ColumnType(length = 255)
    @ColumnComment("告警内容模板（英文）")
    private String alertContentTemplateEn;

    /**
     * 告警内容模板（中文）
     */
    @TableField(value = "alert_content_template_cn")
    @ColumnType(length = 255)
    @ColumnComment("告警内容模板（中文）")
    private String alertContentTemplateCn;

    /**
     * 告警方式
     */
    @TableField(value = "alert_method")
    @ColumnType(length = 20)
    @ColumnComment("告警方式")
    private String alertMethod;

    /**
     * 接收人
     */
    @TableField(value = "recipient")
    @ColumnType(length = 50)
    @ColumnComment("接收人")
    private String recipient;

    /**
     * 生效时间
     */
    @TableField(value = "effective_time")
    @ColumnType(value = MySqlTypeConstant.DATETIME)
    @ColumnComment("生效时间")
    private LocalDateTime effectiveTime;

}
