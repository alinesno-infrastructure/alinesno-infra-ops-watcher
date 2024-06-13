package com.alinesno.infra.ops.watcher.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报警渠道参数数据传输对象
 * 该类用于封装报警渠道的参数信息，包括参数键、参数值、参数描述和是否为内部参数的标志。
 */
@NoArgsConstructor
@Data
public class AlertChannelParamDto {

    /**
     * 参数键
     * 用于唯一标识一个参数。
     */
    private String paramKey;

    /**
     * 参数值
     * 代表参数的具体取值。
     */
    private String paramValue;

    /**
     * 参数描述
     * 用于解释参数的作用或意义。
     */
    private String paramDesc;

    /**
     * 是否为内部参数
     * 标志参数是否仅用于系统内部，对外不可见。
     */
    private boolean isInner;

    /**
     * 构造函数用于创建一个 AlertChannelParamDto 对象。
     *
     * @param paramKey 参数的键，用于唯一标识一个参数。
     * @param paramValue 参数的值，对应于键的特定值。
     * @param paramDesc 参数的描述，解释参数的作用或用途。
     * @param isInner 标志位，表示参数是否为内部使用参数。
     */
    public AlertChannelParamDto(String paramKey, String paramValue, String paramDesc, boolean isInner) {
        this.paramKey = paramKey;
        this.paramValue = paramValue;
        this.paramDesc = paramDesc;
        this.isInner = isInner;
    }

}

