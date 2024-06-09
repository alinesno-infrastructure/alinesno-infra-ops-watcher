package com.alinesno.infra.ops.watcher.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum AlertChannelEnum {

    ALIYUN_VOICE("fa-brands fa-alipay","aliyun_voice", "阿里云语音", "用于发送阿里云语音通知的渠道"),
    DINGTALK("fa-brands fa-slack","dingtalk", "钉钉", "用于发送钉钉通知的渠道"),
    EMAIL("fa-solid fa-envelope-open-text","email", "电子邮件", "用于发送电子邮件通知的渠道"),
    FEISHU("fa-brands fa-telegram","feishu", "飞书", "用于发送飞书通知的渠道"),
    WEBHOOK("fa-brands fa-firefox-browser","webhook", "Webhook", "用于发送Webhook通知的渠道"),
    WECHAT("fa-brands fa-weixin" , "wechat", "微信", "用于发送微信通知的渠道");

    private final String icon;
    private final String code;
    private final String label;
    private final String desc;

    AlertChannelEnum(String icon, String code, String label, String desc) {
        this.icon = icon;
        this.code = code;
        this.label = label;
        this.desc = desc;
    }

    // 从code查找枚举值的方法，考虑到code已更新为完整英文描述，比较时直接使用
    public static AlertChannelEnum fromCode(String code) {
        for (AlertChannelEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static Object[] getAllNames() {
        AlertChannelEnum[] documentTypes = AlertChannelEnum.values();
        Object[] names = new Object[documentTypes.length];
        for (int i = 0; i < documentTypes.length; i++) {
            names[i] = documentTypes[i].getCode();
        }
        return names;
    }

    public static List<AlertChannelEnum> getAllChannels() {
        return Arrays.asList(AlertChannelEnum.values());
    }
}
