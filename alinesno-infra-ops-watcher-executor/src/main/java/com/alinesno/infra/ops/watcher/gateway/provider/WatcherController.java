package com.alinesno.infra.ops.watcher.gateway.provider;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.ops.watcher.dto.MsgDto;
import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.entity.ReportEntity;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
import com.alinesno.infra.ops.watcher.service.IProjectService;
import com.alinesno.infra.ops.watcher.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 报告控制器，处理异常告警消息和获取健康报告信息的接口
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/v1/infra/ops/watcher")
public class WatcherController {

    @Autowired
    private IAlertMessageService alertMessageService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private IProjectService projectService ;

    /**
     * 接收异常告警消息并进行处理
     *
     * @param msgDto 异常告警消息实体
     * @return 响应实体，表示异常告警消息接收成功
     */
    @PostMapping("/alert")
    public AjaxResult receiveAlertMessage(@RequestBody @Validated MsgDto msgDto) {

        log.debug("msgDto = {}" , msgDto);

        boolean isProjectOpen = projectService.isOpen(msgDto.getProjectCode()) ;
        Assert.isTrue(isProjectOpen , "应用"+msgDto.getProjectCode()+"已关闭，不再接收消息通知.");

        AlertMessageEntity alertMessage = new AlertMessageEntity()  ;
        BeanUtils.copyProperties(msgDto , alertMessage);

        //时间戳转LocalDateTime
        long time = msgDto.getTimestamp() ;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);

        alertMessage.setTimestamp(localDateTime);
        alertMessage.setContext(JSONUtil.toJsonStr(msgDto.getContext()));
        alertMessage.setEnvInfo(JSONUtil.toJsonStr(msgDto.getEnvInfo()));

        log.debug("alertMessage = {}" , alertMessage);

        alertMessageService.processAlertMessage(alertMessage);

        return AjaxResult.success("预警消息接收成功.");
    }

    /**
     * 根据报告类型获取健康报告信息
     *
     * @param reportType 报告类型，可选值：daily（当日）、weekly（本周）、monthly（当月）
     * @return 响应实体，包含健康报告信息
     */
    @GetMapping("/health-report")
    public AjaxResult getHealthReport(@RequestParam("type") String reportType) {
        ReportEntity report;
        switch (reportType) {
            case "daily" -> report = reportService.getDailyReport();
            case "weekly" -> report = reportService.getWeeklyReport();
            case "monthly" -> report = reportService.getMonthlyReport();
            default -> {
                return AjaxResult.success();
            }
        }
        return AjaxResult.success();
    }
}
