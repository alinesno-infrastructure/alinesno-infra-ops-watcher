package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.ops.watcher.collector.service.ITraceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import static com.alinesno.infra.ops.watcher.collector.enums.ClickhouseSql.traceSql;

/**
 * 表示 Trace 的 Service业务层处理
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@Service
public class TraceServiceImpl implements ITraceService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveTrace(List<Map<String , Object>> logList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();

            // 关闭自动提交
            // connection.setAutoCommit(false);

            statement = connection.prepareStatement(traceSql);

            for(Map<String , Object> map: logList) {

//                JSONArray jsonArray = JSON.parseArray(traceObj);
//
//                System.out.println(JSONUtil.toJsonPrettyStr(jsonArray));
//
//                for(Object obj : jsonArray) {
                    JSONObject jsonObject = new JSONObject(map);

                    // 验证解析是否正确
                    log.debug(jsonObject.getString("startTimeUnixNano"));

                    // 设置数据
                    statement.setString(1, jsonObject.getString("startTimeUnixNano"));
                    statement.setString(2, jsonObject.getString("traceId"));
                    statement.setString(3, jsonObject.getString("spanId"));
                    statement.setString(4, jsonObject.getString("parentSpanId"));
                    statement.setString(5, jsonObject.getString("traceState"));
                    statement.setString(6, jsonObject.getString("spanName"));
                    statement.setString(7, jsonObject.getString("spanKind"));
                    statement.setString(8, jsonObject.getString("serviceName"));

                    JSONObject resAttr = jsonObject.getJSONObject("resAttr");
                    Map<String, String> resourceAttributes = new HashMap<>();
                    for (String key : resAttr.keySet()) {
                        String value = resAttr.getString(key);
                        resourceAttributes.put(key, value);
                    }
                    statement.setObject(9, resourceAttributes);

                    statement.setString(10, jsonObject.getString("scopeName"));
                    statement.setString(11, jsonObject.getString("scopeVersion"));

                    JSONObject spanAttr = jsonObject.getJSONObject("spanAttr");
                    Map<String, String> spanAttributes = new HashMap<>();
                    for (String key : spanAttr.keySet()) {
                        String value = spanAttr.getString(key);
                        spanAttributes.put(key, value);
                    }
                    statement.setObject(12, spanAttributes);

                    statement.setLong(13, jsonObject.getLong("nanoseconds"));

                    // Set status code as Int8
                    byte statusCode = jsonObject.containsKey("statusCode") && StringUtils.isNoneBlank(jsonObject.getString("statusCode"))
                            ? Byte.parseByte(jsonObject.getString("statusCode"))
                            : (byte) 0; // default value or handle null/invalid case
                    statement.setByte(14, statusCode);

                    statement.setString(15, jsonObject.getString("statusMessage"));
                    statement.setString(16, jsonObject.getString("eventTimes"));
                    statement.setString(17, jsonObject.getString("eventNames"));

                    String eventAttrsJson = convertMapToJsonArray(jsonObject.getJSONObject("eventAttrs"));
                    statement.setString(18, eventAttrsJson);

                    statement.setString(19, jsonObject.getString("linksTraceIDs"));
                    statement.setString(20, jsonObject.getString("linksSpanIDs"));
                    statement.setString(21, jsonObject.getString("linksTraceStates"));

                    String linkAttributesJson = convertMapToJsonArray(jsonObject.getJSONObject("linkAttributes"));
                    statement.setString(22, linkAttributesJson);

                    statement.addBatch();
//                }

                int[] result = statement.executeBatch();

                // 提交事务
                // connection.commit();

                log.debug("链路追踪插入成功:{}" , result.length);
            }

            statement.close();

        } catch (SQLException e) {

            log.error("数据插入异常:{}", e.getMessage());
            // 出现异常时回滚事务
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                log.error("数据插入回滚异常:{}", e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    private String convertMapToJsonArray(JSONObject map) {
        if (map == null || map.isEmpty()) {
            return "[]"; // 返回空数组
        }
        JSONArray array = new JSONArray();
        for (String key : map.keySet()) {
            JSONObject item = new JSONObject();
            item.put(key, map.get(key));
            array.add(item);
        }
        return array.toJSONString();
    }
}
