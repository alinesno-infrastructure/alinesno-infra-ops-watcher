package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.service.IMetricsSumService;
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
import java.util.stream.Collectors;

import static com.alinesno.infra.ops.watcher.collector.enums.ClickhouseSql.metricsSumSql;

/**
 * 表示 MetricsSum 的 Service 业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Service
public class MetricsSumServiceImpl implements IMetricsSumService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveSum(List<Map<String, Object>> logList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();
            // SQL 语句中的字段顺序与下面设置参数的顺序一致
            
            statement = connection.prepareStatement(metricsSumSql);

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
                statement.setDouble(14, jsonObject.getDoubleValue("value"));

                // 设置 flags 参数
                statement.setInt(15, jsonObject.getInteger("flags"));

                statement.addBatch();
            }

            int[] result = statement.executeBatch();
            log.debug("数据插入成功: {}", result);

            statement.close();
        } catch (SQLException e) {
            log.error("数据插入异常: {}", e.getMessage());
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error("关闭连接异常: {}", ex.getMessage());
            }
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("关闭 PreparedStatement 异常: {}", e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭 Connection 异常: {}", e.getMessage());
                }
            }
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
