package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alinesno.infra.ops.watcher.collector.service.IUniversalLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UniversalLogsServiceImpl implements IUniversalLogsService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // SQL语句应根据实际表结构进行调整
    private static final String INSERT_LOG_SQL = 
        "INSERT INTO universal_logs (id, add_time, delete_time, update_time, has_status, operator_id, field_prop, last_update_operator_id, has_delete, delete_manager, application_id, application_name, org_id, field_id, department_id, log_type, source, level, message, extra_fields) " +
        "VALUES (:id, :addTime, :deleteTime, :updateTime, :hasStatus, :operatorId, :fieldProp, :lastUpdateOperatorId, :hasDelete, :deleteManager, :applicationId, :applicationName, :orgId, :fieldId, :departmentId, :logType, :source, :level, :message, :extraFields)";

    public UniversalLogsServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveLogs(List<Map<String, Object>> logList) {
        try {
            List<MapSqlParameterSource> batchValues = logList.stream()
                .map(this::createSqlParameterSource)
                .toList();

            namedParameterJdbcTemplate.batchUpdate(INSERT_LOG_SQL, batchValues.toArray(new MapSqlParameterSource[0]));
        } catch (Exception e) {
            // 处理异常，例如记录日志或抛出自定义异常
            log.error(e.getMessage());
            throw new RuntimeException("Failed to save logs", e);
        }
    }

    private MapSqlParameterSource createSqlParameterSource(Map<String, Object> logEntry) {
        return new MapSqlParameterSource()
            .addValue("id", logEntry.get("id"))
            .addValue("addTime", logEntry.get("add_time"))
            .addValue("deleteTime", logEntry.get("delete_time"))
            .addValue("updateTime", logEntry.get("update_time"))
            .addValue("hasStatus", logEntry.get("has_status"))
            .addValue("operatorId", logEntry.get("operator_id"))
            .addValue("fieldProp", logEntry.get("field_prop"))
            .addValue("lastUpdateOperatorId", logEntry.get("last_update_operator_id"))
            .addValue("hasDelete", logEntry.get("has_delete"))
            .addValue("deleteManager", logEntry.get("delete_manager"))
            .addValue("applicationId", logEntry.get("application_id"))
            .addValue("applicationName", logEntry.get("application_name"))
            .addValue("orgId", logEntry.get("org_id"))
            .addValue("fieldId", logEntry.get("field_id"))
            .addValue("departmentId", logEntry.get("department_id"))
            .addValue("logType", logEntry.get("log_type"))
            .addValue("source", logEntry.get("source"))
            .addValue("level", logEntry.get("level"))
            .addValue("message", logEntry.get("message"))
            .addValue("extraFields", logEntry.get("extra_fields"), Types.JAVA_OBJECT); // 假设extra_fields是Map<String, String>
    }

}