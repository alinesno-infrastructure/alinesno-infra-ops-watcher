package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.service.IMetricsHistogramService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MetricsHistogramServiceImpl implements IMetricsHistogramService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveHistogram(List<Map<String, Object>> logList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();
            // 确保这里的 SQL 语句中的字段顺序与下面设置参数的顺序一致
            String metricsHistogramSql = "INSERT INTO telemetry_metrics_histogram (" +
                    "resource_attributes, " +
                    "resource_schema_url, " +
                    "scope_name, " +
                    "scope_version, " +
                    "scope_attributes, " +
                    "scope_dropped_attr_count, " +
                    "scope_schema_url, " +
                    "metric_name, " +
                    "metric_description, " +
                    "metric_unit, " +
                    "attributes, " +
                    "start_time_unix_nano, " +
                    "time_unix_nano, count, " +
                    "sum, " +
                    "bucket_counts, " +
                    "explicit_bounds, " +
                    "exemplars_filtered_attributes, " +
                    "exemplars_time_unix_nano, " +
                    "exemplars_value, " +
                    "exemplars_span_id, " +
                    "exemplars_trace_id, " +
                    "flags, " +
                    "min, " +
                    "max) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(metricsHistogramSql);

            for (Map<String, Object> map : logList) {
                JSONObject jsonObject = new JSONObject(map);
                // 设置 resource_attributes 参数
                JSONObject resourceAttributes = jsonObject.getJSONObject("resourceAttributes");
                Map<String, String> resAttr = convertJsonToMap(resourceAttributes);
                statement.setObject(1, resAttr);

                // 设置其他字符串类型参数
                statement.setString(2, jsonObject.getString("resourceSchemaUrl"));
                statement.setString(3, jsonObject.getString("scopeName"));
                statement.setString(4, jsonObject.getString("scopeVersion"));

                // 设置 scope_attributes 参数
                JSONObject scopeAttributes = jsonObject.getJSONObject("scopeAttributes");
                Map<String, String> scoAttr = convertJsonToMap(scopeAttributes);
                statement.setObject(5, scoAttr);

                // 设置整数类型参数
                statement.setInt(6, jsonObject.getInteger("scopeDroppedAttrCount"));

                // 设置其他字符串类型参数
                statement.setString(7, jsonObject.getString("scopeSchemaUrl"));
                statement.setString(8, jsonObject.getString("metricName"));
                statement.setString(9, jsonObject.getString("metricDescription"));
                statement.setString(10, jsonObject.getString("metricUnit"));

                // 设置 attributes 参数
                JSONObject attributes = jsonObject.getJSONObject("attributes");
                Map<String, String> attr = convertJsonToMap(attributes);
                statement.setObject(11, attr);

                // 设置时间戳类型参数
                statement.setLong(12, jsonObject.getLong("startTimeUnix"));
                statement.setLong(13, jsonObject.getLong("timeUnix"));

                // 设置数值类型参数
                statement.setLong(14, jsonObject.getLongValue("count"));
                statement.setDouble(15, jsonObject.getDoubleValue("sum"));

                // 如果 bucketCounts 是 JSONArray 类型
                Object bucketCounts = jsonObject.get("bucketCounts");
                statement.setString(16, String.valueOf(bucketCounts));

                JSONArray explicitBounds = jsonObject.getJSONArray("explicitBounds");
                statement.setArray(17, connection.createArrayOf("DOUBLE", explicitBounds.toArray()));

                // 设置 exemplars.filtered_attributes 参数
                Object exemplarsFilteredAttributes = jsonObject.get("exemplarsFilteredAttributes");
                if(exemplarsFilteredAttributes instanceof JSONArray){
                    statement.setArray(18, connection.createArrayOf("STRING", jsonObject.getJSONArray("exemplarsFilteredAttributes").toArray()));
                }else{
                    statement.setObject(18, exemplarsFilteredAttributes);
                }

                // 设置 exemplars.time_unix_nano 参数
                JSONArray exemplarsTimeUnixNano = jsonObject.getJSONArray("exemplarsTimeUnix");
                statement.setArray(19, connection.createArrayOf("BIGINT", exemplarsTimeUnixNano.toArray()));

                // 设置 exemplars.value 参数
                JSONArray exemplarsValue = jsonObject.getJSONArray("exemplarsValue");
                statement.setArray(20, connection.createArrayOf("DOUBLE", exemplarsValue.toArray()));

                // 设置 exemplars.span_id 参数
                JSONArray exemplarsSpanId = jsonObject.getJSONArray("exemplarsSpanId");
                statement.setArray(21, connection.createArrayOf("VARCHAR", exemplarsSpanId.toArray()));

                // 设置 exemplars.trace_id 参数
                JSONArray exemplarsTraceId = jsonObject.getJSONArray("exemplarsTraceId");
                statement.setArray(22, connection.createArrayOf("VARCHAR", exemplarsTraceId.toArray()));

                // 设置 flags 参数
                statement.setInt(23, jsonObject.getInteger("flags") == null ? 0 : jsonObject.getInteger("flags"));

                // 设置 min 和 max 参数
                statement.setDouble(24, jsonObject.getDoubleValue("min"));
                statement.setDouble(25, jsonObject.getDoubleValue("max"));

                statement.addBatch();
            }

            int[] result = statement.executeBatch();
            log.debug("批量插入MetricsHistogram结果: {}", result);

            statement.close();
        } catch (SQLException e) {
            log.error("数据插入异常: {}", e.getMessage());
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error("关闭连接异常: {}", ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    // 辅助方法：将 JSONObject 转换为 Map<String, String>
    private static Map<String, String> convertJsonToMap(JSONObject jsonObject) {
        Map<String, String> resultMap = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            // 如果值是 JSONArray 或者 JSONObject，则转换成字符串
            if (value instanceof JSONObject || value instanceof JSONArray) {
                resultMap.put(key, value.toString());
            } else {
                resultMap.put(key, String.valueOf(value));
            }
        }
        return resultMap;
    }
}