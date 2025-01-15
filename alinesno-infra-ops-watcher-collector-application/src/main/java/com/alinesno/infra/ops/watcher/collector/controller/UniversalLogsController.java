package com.alinesno.infra.ops.watcher.collector.controller;

import com.alinesno.infra.ops.watcher.collector.service.IUniversalLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接收通用日志并保存到clickhouse当中
 */
@Slf4j
@RestController
@RequestMapping("/api/universalLogs")
public class UniversalLogsController {

    @Autowired
    private IUniversalLogsService universalLogsService;

    @PostMapping("/saveBatch")
    public ResponseEntity<String> saveBatchLog(@RequestBody List<Map<String, Object>> logList) {
        try {
            // 将每条日志的主要字段转换为extra_fields
            List<Map<String, Object>> processedLogList = logList.stream()
                    .map(this::processLogEntry)
                    .collect(Collectors.toList());

            // 调用服务层保存日志
            universalLogsService.saveLogs(processedLogList);

            return new ResponseEntity<>("Logs saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>("Failed to save logs: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveLog(@RequestBody Map<String, Object> logEntry) {
        try {
            // 将日志的主要字段转换为extra_fields
            Map<String, Object> processedLogEntry = processLogEntry(logEntry);

            // 调用服务层保存日志
            universalLogsService.saveLogs(List.of(processedLogEntry));

            return new ResponseEntity<>("Log saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>("Failed to save log: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Object> processLogEntry(Map<String, Object> logEntry) {
        // 定义固定字段和额外字段的映射
        Map<String, Object> fixedFields = new HashMap<>();
        Map<String, String> extraFields = new HashMap<>();

        // 固定字段列表
        String[] fixedFieldNames = {
                "id",
                "add_time",
                "delete_time",
                "update_time",
                "has_status",
                "operator_id",
                "field_prop",
                "last_update_operator_id",
                "has_delete",
                "delete_manager",
                "application_id",
                "application_name",
                "org_id",
                "field_id",
                "department_id",
                "log_type",
                "source",
                "level",
                "message"
        };

        // 分离固定字段和额外字段
        for (String fieldName : fixedFieldNames) {
            if (logEntry.containsKey(fieldName)) {
                fixedFields.put(fieldName, logEntry.get(fieldName));
            }
        }

        // 剩余字段视为额外字段
        logEntry.forEach((key, value) -> {
            if (!fixedFields.containsKey(key)) {
                extraFields.put(key, value.toString());
            }
        });

        // 将extra_fields添加到固定字段中
        fixedFields.put("extra_fields", extraFields);

        return fixedFields;
    }
}