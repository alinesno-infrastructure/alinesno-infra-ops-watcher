package com.alinesno.infra.ops.watcher.enums;

import com.alinesno.infra.ops.watcher.dto.AlertChannelParamDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 报警渠道枚举类，定义了各种报警通知的渠道。
 */
@Getter
public enum AlertChannelEnum {

    // 定义了阿里云语音通知的枚举常量
    ALIYUN_VOICE("fa-brands fa-alipay","aliyun_voice", "阿里云语音", "用于发送阿里云语音通知的渠道"),
    // 定义了阿里云短信通知的枚举常量
    ALIYUN_SMS("fa-brands fa-weixin","aliyun_sms", "阿里云短信", "用于发送阿里云短信通知的渠道"),
    // 定义了钉钉通知的枚举常量
    DINGTALK("fa-brands fa-slack","dingtalk", "钉钉", "用于发送钉钉通知的渠道"),
    // 定义了电子邮件通知的枚举常量
    EMAIL("fa-solid fa-envelope-open-text","email", "电子邮件", "用于发送电子邮件通知的渠道"),
    // 定义了飞书通知的枚举常量
    FEISHU("fa-brands fa-telegram","feishu", "飞书", "用于发送飞书通知的渠道"),
    // 定义了Webhook通知的枚举常量
    WEBHOOK("fa-brands fa-firefox-browser","webhook", "Webhook", "用于发送Webhook通知的渠道"),
    // 定义了微信通知的枚举常量
    WECHAT("fa-brands fa-weixin" , "wechat", "微信", "用于发送微信通知的渠道");

    // 图标类名，用于前端展示
    private final String icon;
    // 渠道代码，用于标识报警渠道
    private final String code;
    // 渠道名称，用于前端展示
    private final String label;
    // 渠道描述，用于说明该渠道的作用
    private final String desc;

    /**
     * 构造方法，初始化报警渠道枚举常量。
     * @param icon 图标类名
     * @param code 渠道代码
     * @param label 渠道名称
     * @param desc 渠道描述
     */
    AlertChannelEnum(String icon, String code, String label, String desc) {
        this.icon = icon;
        this.code = code;
        this.label = label;
        this.desc = desc;
    }

    /**
     * 根据渠道代码获取对应的报警渠道枚举常量。
     * @param code 渠道代码
     * @return 对应的报警渠道枚举常量
     * @throws IllegalArgumentException 如果代码无效，则抛出此异常
     */
    // 从code查找枚举值的方法，考虑到code已更新为完整英文描述，比较时直接使用
    public static AlertChannelEnum fromCode(String code) {
        for (AlertChannelEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    /**
     * 获取所有报警渠道名称的列表。
     * @return 所有报警渠道名称的列表
     */
    public static Object[] getAllNames() {
        AlertChannelEnum[] documentTypes = AlertChannelEnum.values();
        Object[] names = new Object[documentTypes.length];
        for (int i = 0; i < documentTypes.length; i++) {
            names[i] = documentTypes[i].getCode();
        }
        return names;
    }

    /**
     * 获取所有报警渠道的列表。
     * @return 所有报警渠道的列表
     */
    public static List<AlertChannelEnum> getAllChannels() {
        return Arrays.asList(AlertChannelEnum.values());
    }

    /**
     * 根据渠道代码获取警报渠道的参数配置。
     * 该方法通过switch-case语句根据传入的channelCode确定警报渠道，然后为每个渠道添加相应的参数配置。
     * 参数配置以AlertChannelParamDto对象的形式添加到一个列表中，并将该列表作为方法的返回值。
     *
     * @param channelCode 警报渠道的代码，用于确定警报渠道和对应的参数配置。
     * @return 返回一个包含警报渠道参数配置的列表。如果传入的渠道代码不匹配任何已知渠道，则返回一个空列表。
     */
    public static List<AlertChannelParamDto> getChannelParams(String channelCode) {
        // 初始化一个空的参数列表
        List<AlertChannelParamDto> listParam = new ArrayList<>();

        // 根据渠道代码选择警报渠道并配置参数
        switch (channelCode) {
            case "wechat":
                // 配置微信警报渠道的参数
                listParam.add(new AlertChannelParamDto("wechat_param1", "value1", "Description 1", true));
                listParam.add(new AlertChannelParamDto("wechat_param2", "value2", "Description 2", true));
                break;

            case "aliyun_voice":
                // 配置阿里云语音警报渠道的参数
                listParam.add(new AlertChannelParamDto("aliyun_voice_param1", "value1", "Description 1", true));
                listParam.add(new AlertChannelParamDto("aliyun_voice_param2", "value2", "Description 2", true));
                break;

            case "aliyun_sms":
                // 配置阿里云短信警报渠道的参数
                listParam.add(new AlertChannelParamDto("aliyun.sms.app_key", "", "AppKey从控制台获取", true));
                listParam.add(new AlertChannelParamDto("aliyun.sms.app_secret", "", "AppSecret从控制台获取", true));
                listParam.add(new AlertChannelParamDto("aliyun.sms.sign_name", "", "签名名称从控制台获取，必须是审核通过的", true));
                listParam.add(new AlertChannelParamDto("aliyun.sms.template_code", "", "模板CODE从控制台获取，必须是审核通过的", true)) ;
                listParam.add(new AlertChannelParamDto("aliyun.sms.host", "", "API域名从控制台获取", true));
                break;

            case "dingtalk":
                // 配置钉钉警报渠道的参数
                listParam.add(new AlertChannelParamDto("dingtalk_param1", "value1", "Description 1", true));
                listParam.add(new AlertChannelParamDto("dingtalk_param2", "value2", "Description 2", true));
                break;

            case "email":
                // 配置电子邮件警报渠道的参数
                listParam.add(new AlertChannelParamDto("email_param1", "value1", "Description 1", true));
                listParam.add(new AlertChannelParamDto("email_param2", "value2", "Description 2", true));
                break;

            case "feishu":
                // 配置飞书警报渠道的参数
                listParam.add(new AlertChannelParamDto("feishu_param1", "value1", "Description 1", true));
                listParam.add(new AlertChannelParamDto("feishu_param2", "value2", "Description 2", true));
                break;

            case "webhook":
                // 配置Webhook警报渠道的参数
                listParam.add(new AlertChannelParamDto("webhook_param1", "value1", "Description 1", true));
                listParam.add(new AlertChannelParamDto("webhook_param2", "value2", "Description 2", true));
                break;

            default:
                // 默认情况下，如果渠道代码不匹配任何已知渠道，则不添加任何参数
                // 默认情况下返回空列表
                break;
        }

        // 返回配置好的参数列表
        return listParam;
    }


}
