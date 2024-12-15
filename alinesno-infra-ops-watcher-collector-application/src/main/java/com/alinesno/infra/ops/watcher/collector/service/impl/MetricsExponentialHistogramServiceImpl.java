package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.service.IMetricsExponentialHistogramService;
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

import static com.alinesno.infra.ops.watcher.collector.enums.ClickhouseSql.metricsExponentialHistogramSql;

/**
 * 表示 MetricsExponentialHistogram 的 Service 业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Slf4j
@Service
public class MetricsExponentialHistogramServiceImpl implements IMetricsExponentialHistogramService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveHistogram(List<Map<String, Object>> logList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();

            // 关闭自动提交
            // connection.setAutoCommit(false);

            statement = connection.prepareStatement(metricsExponentialHistogramSql);

            // JSONArray jsonArray = JSON.parseArray(logList);
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
                statement.setObject(11, attr);
                statement.setLong(12, jsonObject.getLong("startTimeUnix"));
                statement.setLong(13, jsonObject.getLong("timeUnix"));
                statement.setLong(14, jsonObject.getLong("count"));
                statement.setDouble(15, jsonObject.getDouble("sum"));
                statement.setLong(16, jsonObject.getLong("scale"));
                statement.setLong(17, jsonObject.getLong("zeroCount"));
                statement.setLong(18, jsonObject.getLong("positiveOffset"));
                statement.setObject(19, jsonObject.getJSONArray("positiveBucketCounts").toString());
                statement.setLong(20, jsonObject.getLong("negativeOffset"));
                statement.setObject(21, jsonObject.getJSONArray("negativeBucketCounts").toString());

                // TODO exemplarsFilteredAttributes 数据为 Map 类型，但是数据库对应字段类型为 Array，调整 exemplarsFilteredAttributes 数据以正确插入数据库中
//                statement.setString(22, jsonObject.getString("exemplarsFilteredAttributes"));

                statement.setString(23, jsonObject.getString("exemplarsTimeUnix"));
                statement.setString(24, jsonObject.getString("exemplarsValue"));
                statement.setString(25, jsonObject.getString("exemplarsSpanId"));
                statement.setString(26, jsonObject.getString("exemplarsTraceId"));

                statement.setLong(27, jsonObject.getLong("flags"));
                statement.setDouble(28, jsonObject.getDouble("min"));
                statement.setDouble(29, jsonObject.getDouble("max"));


                statement.addBatch();

                int[] result = statement.executeBatch();

                // 提交事务
                // connection.commit();
                log.debug("数据插入成功:{}", result.length);
            }


            statement.close();
        } catch (SQLException e) {
            log.error("数据插入异常:{}", e.getMessage());
            // 出现异常时回滚事务
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                log.error("数据插入回滚异常:{}", ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }
}
