package com.alinesno.infra.ops.watcher.dto;

import com.alinesno.infra.ops.watcher.enums.AlertLevelEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

/**
 * 监控的异常信息
 */
@ToString
@Data
public class MsgDto {

    /**
     * 异常或事件的描述信息。
     */
    @NotBlank(message = "预警信息不能为空.")
    private String description;

    /**
     * 异常的错误信息，通常为异常堆栈的简要描述。
     */
    private String errorMsg;

    /**
     * 项目的唯一标识码。
     */
    @NotBlank(message = "项目标识不能为空.")
    private String projectCode ;

    /**
     * 异常或事件的等级，用于区分不同严重程度的问题。
     * 默认为重要等级。
     */
    private String level = AlertLevelEnum.IMPORTANT.getCode() ;

    /**
     * 异常或事件的分类，用于对问题进行初步归类。
     */
    @NotBlank(message = "渠道标识不能为空.")
    private String category; // 预警信息分类

    /**
     * 异常或事件发生的时间戳。
     */
    private long timestamp;

    /**
     * 异常上下文信息，用于提供问题发生的上下文环境，如相关的请求ID等。
     */
    private Object context;

    /**
     * 环境信息，用于描述问题发生的环境，如生产环境、测试环境等。
     */
    private Object envInfo;

}
