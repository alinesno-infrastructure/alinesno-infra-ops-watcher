package com.alinesno.infra.ops.watcher.api.provider;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.ops.watcher.entity.AlertMessageEntity;
import com.alinesno.infra.ops.watcher.entity.ReportEntity;
import com.alinesno.infra.ops.watcher.service.IAlertMessageService;
import com.alinesno.infra.ops.watcher.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 报告控制器，处理异常告警消息和获取健康报告信息的接口
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@RestController
@RequestMapping("/v1/api/watcher")
public class WatcherController {

    @Autowired
    private IAlertMessageService alertMessageService;

    @Autowired
    private IReportService reportService;

    /**
     * 接收异常告警消息并进行处理
     *
     * @param alertMessage 异常告警消息实体
     * @return 响应实体，表示异常告警消息接收成功
     */
    @PostMapping("/alert")
    public AjaxResult receiveAlertMessage(@RequestBody AlertMessageEntity alertMessage) {
        alertMessageService.processAlertMessage(alertMessage);
        return AjaxResult.success("Alert message received successfully.");
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
