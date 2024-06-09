package com.alinesno.infra.ops.watcher.enums;

import lombok.Getter;
import java.util.Arrays;
import java.util.List;

/**
 * 预警状态枚举类，用于定义预警信息的状态。
 * 包含已发送、待处理、处理中和发送失败四种状态。
 */
@Getter
public enum AlertStatusEnum {

    // 已发送状态，代码为"1"，描述为"预警信息已发送"
    SENT("1", "已发送", "预警信息已发送"),
    // 待处理状态，代码为"2"，描述为"预警信息待处理"
    PENDING("2", "待处理", "预警信息待处理"),
    // 处理中状态，代码为"3"，描述为"预警信息处理中"
    IN_PROGRESS("3", "处理中", "预警信息处理中"),
    // 发送失败状态，代码为"9"，描述为"预警信息发送失败"
    FAILED("9", "发送失败", "预警信息发送失败");

    // 状态代码
    private final String code;
    // 状态标签，用于展示
    private final String label;
    // 状态描述，详细说明状态含义
    private final String desc;

    /**
     * 构造方法，初始化预警状态枚举。
     * @param code 状态代码
     * @param label 状态标签
     * @param desc 状态描述
     */
    AlertStatusEnum(String code, String label, String desc) {
        this.code = code;
        this.label = label;
        this.desc = desc;
    }

    /**
     * 根据代码获取对应的预警状态枚举值。
     * @param code 状态代码
     * @return 对应的预警状态枚举值
     * @throws IllegalArgumentException 如果代码无效，则抛出此异常
     */
    public static AlertStatusEnum fromCode(String code) {
        for (AlertStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    /**
     * 获取所有状态代码。
     * @return 所有状态代码的数组
     */
    public static Object[] getAllCodes() {
        AlertStatusEnum[] statuses = AlertStatusEnum.values();
        Object[] codes = new Object[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            codes[i] = statuses[i].getCode();
        }
        return codes;
    }

    /**
     * 获取所有预警状态枚举值。
     * @return 所有预警状态枚举值的列表
     */
    public static List<AlertStatusEnum> getAllStatuses() {
        return Arrays.asList(AlertStatusEnum.values());
    }
}
