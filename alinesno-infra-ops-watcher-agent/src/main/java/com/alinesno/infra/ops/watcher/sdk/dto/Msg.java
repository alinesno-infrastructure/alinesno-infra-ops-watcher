package com.alinesno.infra.ops.watcher.sdk.dto;

import lombok.Data;

/**
 * 监控的异常信息
 */
@Data
public class Msg {

    // -->>>>>>>>>>>>>> 用户填写部分 -->>>>>>>>>>>>.
    private String alarmName ; //	可选	告警标题，alarmName与eventId不能同时为空
    private String alarmContent	 ; //	必须	告警内容详情
    // -->>>>>>>>>>>>>> 用户填写部分 -->>>>>>>>>>>>.

    private String projectCode ; //	必须	需要告警集成的项目代码
    private String level ; //	可选	提醒 1，警告 2，严重 3，通知 4，致命 5
    private Env env ; // 运行环境
    private String contexts ; //	可选	上下文

}
