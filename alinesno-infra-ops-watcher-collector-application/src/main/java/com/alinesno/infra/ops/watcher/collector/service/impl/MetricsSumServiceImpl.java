package com.alinesno.infra.ops.watcher.collector.service.impl;

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

            // 关闭自动提交
            // connection.setAutoCommit(false);

            statement = connection.prepareStatement(metricsSumSql);

//            JSONArray jsonArray = JSON.parseArray(logList);
            for(Map<String, Object> map : logList) {
                JSONObject jsonObject = new JSONObject(map);

                JSONObject resourceAttributes = jsonObject.getJSONObject("resourceAttributes");
                Map<String, String> resAttr = new HashMap<>();
                for (String key : resourceAttributes.keySet()) {
                    String value = resourceAttributes.getString(key);
                    resAttr.put(key, value);
                }
                statement.setObject(1, resAttr);
                statement.setString(2, jsonObject.getString("resourceSchemaUrl"));
                statement.setString(3, jsonObject.getString("scopeName"));
                statement.setString(4, jsonObject.getString("scopeVersion"));
                JSONObject scopeAttributes = jsonObject.getJSONObject("scopeAttributes");
                Map<String, String> scoAttr = new HashMap<>();
                for (String key : scopeAttributes.keySet()) {
                    String value = scopeAttributes.getString(key);
                    scoAttr.put(key, value);
                }
                statement.setObject(5, scoAttr);
                statement.setString(6, jsonObject.getString("scopeDroppedAttrCount"));
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
                statement.setObject(11, attr);
                statement.setString(12, jsonObject.getString("startTimeUnix"));
                statement.setString(13, jsonObject.getString("timeUnix"));
                statement.setString(14, jsonObject.getString("value"));
                statement.setLong(15, jsonObject.getLong("flags"));

                // TODO exemplarsFilteredAttributes 数据为 Map 类型，但是数据库对应字段类型为 Array，调整 exemplarsFilteredAttributes 数据以正确插入数据库中
//                statement.setString(16, jsonObject.getString("exemplarsFilteredAttributes"));
//
//                statement.setObject(17, jsonObject.getString("exemplarsTimeUnix"));
//                statement.setString(18, jsonObject.getString("exemplarsValue"));
//                statement.setString(19, jsonObject.getString("exemplarsSpanId"));
//                statement.setString(20, jsonObject.getString("exemplarsTraceId"));
//
//                // TODO aggTemp 的类型为 AggregationTemporality，数据库对应字段的类型为 Int32，调整aggTemp数据以正确插入数据库
//                statement.setString(21, jsonObject.getString("aggTemp"));
//
//                statement.setString(22, jsonObject.getString("ssMonotonic"));

                statement.addBatch();
                int[] result = statement.executeBatch();

                // 提交事务
                // connection.commit();

                log.debug("数据插入成功: {}", result);
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
        }
    }
}
