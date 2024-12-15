package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.service.IMetricsSummaryService;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.alinesno.infra.ops.watcher.collector.enums.ClickhouseSql.metricsSummarySql;

/**
 * 表示 MetricsSummary 的 Service 业务层处理
 *
 * @version 1.0.0
 * @author luoxiaodong
 */
@Service
public class MetricsSummaryServiceImpl implements IMetricsSummaryService {
    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(MetricsSummaryServiceImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveSummary(List<Map<String, Object>> logList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();

            // 关闭自动提交
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(metricsSummarySql);

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

                statement.setLong(12, jsonObject.getLong("startTimeUnix"));
                statement.setLong(13, jsonObject.getLong("timeUnix"));
                statement.setLong(14, jsonObject.getLong("count"));
                statement.setDouble(15, jsonObject.getDouble("sum"));
                statement.setString(16, jsonObject.getString("valueAtQuantilesQuantile"));
                statement.setString(17, jsonObject.getString("valueAtQuantilesValue"));
                statement.setLong(18, jsonObject.getLong("flags"));

                statement.addBatch();

                int[] result = statement.executeBatch();

                // 提交事务
                connection.commit();
            }


            statement.close();
        } catch (SQLException e) {
            log.error("数据插入异常: {}", e.getMessage());
            // 出现异常时回滚事务
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                log.error("数据插入回滚异常: {}", ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }
}
