package com.alinesno.infra.ops.watcher.scheduler.dto;

import com.alinesno.infra.common.facade.base.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密钥数据传输对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecretsDto extends BaseDto {

    @NotBlank(message = "名称不能为空")
    private String secName;

    @NotBlank(message = "值不能为空")
    private String secValue;

    @NotBlank(message = "描述不能为空")
    private String secDesc;

    @NotBlank(message = "范围不能为空")
    private String secScope;

}
