package com.alinesno.infra.ops.watcher.collector.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alinesno.infra.ops.watcher.collector.service.IMetricsGaugeService;
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

import static com.alinesno.infra.ops.watcher.collector.enums.ClickhouseSql.metricsGaugeSql;

/**
 * 表示 MetricsGauge 的 Service 业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Service
public class MetricsGaugeServiceImpl implements IMetricsGaugeService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveGauge(List<Map<String, Object>> logList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();

            // 关闭自动提交
            // connection.setAutoCommit(false);

            statement = connection.prepareStatement(metricsGaugeSql);

//            JSONArray jsonArray = JSON.parseArray(logList);

            for(Map<String, Object> map: logList) {
                JSONObject jsonObject = new JSONObject(map);

                log.debug(JSONUtil.toJsonPrettyStr(jsonObject));

                JSONObject resourceAttributes = jsonObject.getJSONObject("resourceAttributes");
                Map<String, String> resAttr = new HashMap<>();
                for (String key : resourceAttributes.keySet()) {
                    String value = resourceAttributes.getString(key);
                    resAttr.put(key, value);
                }
                statement.setString(1, JSONObject.toJSONString(resAttr));
                statement.setString(2, jsonObject.getString("resourceSchemaUrl"));
                statement.setString(3, jsonObject.getString("scopeName"));
                statement.setString(4, jsonObject.getString("scopeVersion"));
                JSONObject scopeAttributes = jsonObject.getJSONObject("scopeAttributes");

                Map<String, String> scoAttr = new HashMap<>();
                for (String key : scopeAttributes.keySet()) {
                    String value = scopeAttributes.getString(key);
                    scoAttr.put(key, value);
                }
                statement.setString(5, JSONObject.toJSONString(scoAttr));

                statement.setLong(6, jsonObject.getLong("scopeDroppedAttrCount"));
                statement.setString(7, jsonObject.getString("scopeSchemaUrl"));
                statement.setString(8, jsonObject.getString("metricName"));
                statement.setString(9, jsonObject.getString("metricDescription"));
                statement.setString(10, jsonObject.getString("metricUnit"));
                JSONObject attributes = jsonObject.getJSONObject("attributes");
                Map<String, String> attr = new HashMap<>();
                for (String key : attributes.keySet()) {
                    String value = attributes.getString(key);
                    attr.put(key, value);
                }
                statement.setString(11, JSONObject.toJSONString(attr));
                statement.setString(12, jsonObject.getString("startTimeUnix"));
                statement.setString(13, jsonObject.getString("timeUnix"));
                statement.setDouble(14, jsonObject.getDouble("value"));
                statement.setLong(15, jsonObject.getLong("flags"));

                // 将 JSON 数组字符串解析为 List<Map<String, String>>
                String exemplarsFilteredAttributesStr = jsonObject.getString("exemplarsFilteredAttributes");

                if (exemplarsFilteredAttributesStr == null || exemplarsFilteredAttributesStr.trim().isEmpty()) {
                    // 如果字段为空，则提供一个空数组作为默认值
                    exemplarsFilteredAttributesStr = "[]";
                } else {
                    // 解析 JSON 数组字符串为 List<Map<String, String>>
                    List<Map<String, String>> exemplarsFilteredAttributesList;
                    try {
                        exemplarsFilteredAttributesList = JSON.parseObject(exemplarsFilteredAttributesStr.toString(),
                                new TypeReference<List<Map<String, String>>>(){});

                        // 将 List<Map<String, String>> 转换回 JSON 字符串，确保格式正确
                        exemplarsFilteredAttributesStr = JSON.toJSONString(exemplarsFilteredAttributesList);
                    } catch (Exception e) {
                        log.error("无法解析 exemplarsFilteredAttributes: {}", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }

                // 设置 PreparedStatement 参数时，确保传递的是一个有效的 JSON 数组字符串
//                statement.setString(16, JSONObject.toJSONString(exemplarsFilteredAttributesStr));
//
//                statement.setObject(17, jsonObject.getString("exemplarsTimeUnix"));
//                statement.setString(18, jsonObject.getString("exemplarsValue"));
//                statement.setString(19, jsonObject.getString("exemplarsSpanId"));
//                statement.setString(20, jsonObject.getString("exemplarsTraceId"));

                statement.addBatch();

                int[] result = statement.executeBatch();

                // 提交事务
                // connection.commit();
                log.debug("指标Gauge插入成功: {}", result);
            }


            statement.close();
        } catch (SQLException e) {
            log.error("数据插入异常: {}", e.getMessage());
            // 出现异常时回滚事务
            try {
                // connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                log.error("数据插入回滚异常: {}", ex.getMessage());
            }
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("关闭PreparedStatement异常: {}", e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭Connection异常: {}", e.getMessage());
                }
            }
        }
    }
}
