package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alinesno.infra.ops.watcher.collector.bean.PerformanceInfo;
import com.alinesno.infra.ops.watcher.collector.bean.TerminalInfo;
import com.alinesno.infra.ops.watcher.collector.service.IUserPerformanceLogService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
public class UserPerformanceLogServiceImpl implements IUserPerformanceLogService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveUserPerformanceLogs(TerminalInfo terminalInfo, PerformanceInfo performanceInfo, String uid, String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();
            String insertSql = "INSERT INTO font_user_performance_logs (" +
                    "resolving_power, " +
                    "referrer, " +
                    "ua, " +
                    "browser_json, " +
                    "engine_json, " +
                    "os_json, " +
                    "device_json, " +
                    "cpu_json, " +
                    "ip, " +
                    "city, " +
                    "fp, " +
                    "redirect_time, " +
                    "domain_lookup_time, " +
                    "connect_time, " +
                    "response_time, " +
                    "dom_complete_time, " +
                    "fcp, " +
                    "ttfb, " +
                    "lcp, " +
                    "fid, " +
                    "uid, " +
                    "id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(insertSql);

            // 设置终端信息字段
            statement.setString(1, terminalInfo.getResolving_power());
            statement.setString(2, terminalInfo.getReferrer());
            statement.setString(3, terminalInfo.getUa());
            statement.setString(4, toJsonString(terminalInfo.getBrowser()));
            statement.setString(5, toJsonString(terminalInfo.getEngine()));
            statement.setString(6, toJsonString(terminalInfo.getOs()));
            statement.setString(7, toJsonString(terminalInfo.getDevice()));
            statement.setString(8, toJsonString(terminalInfo.getCpu()));
            statement.setString(9, terminalInfo.getIp());
            statement.setString(10, terminalInfo.getCity());

            // 设置性能信息字段
            statement.setInt(11, performanceInfo.getFp());
            statement.setInt(12, performanceInfo.getRedirect_time());
            statement.setInt(13, performanceInfo.getDomain_lookup_time());
            statement.setInt(14, performanceInfo.getConnect_time());
            statement.setInt(15, performanceInfo.getResponse_time());
            statement.setInt(16, performanceInfo.getDom_complete_time());
            statement.setDouble(17, performanceInfo.getFcp() == null ? 0.0 : performanceInfo.getFcp());
            statement.setDouble(18, performanceInfo.getTtfb() == null ? 0.0 : performanceInfo.getTtfb());
            statement.setDouble(19, performanceInfo.getLcp() == null ? 0.0 : performanceInfo.getLcp() );
            statement.setDouble(20, performanceInfo.getFid() == null ? 0.0 : performanceInfo.getFid());

            // 设置用户唯一标识符和日志配置ID
            statement.setString(21, uid);
            statement.setString(22, id);

            int result = statement.executeUpdate();
            log.debug("插入UserPerformanceLog结果: {}", result);

            statement.close();
        } catch (SQLException e) {
            log.error("数据插入异常: {}", e.getMessage());
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                log.error("关闭连接异常: {}", ex.getMessage());
            }
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("关闭语句异常: {}", e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭连接异常: {}", e.getMessage());
                }
            }
        }
    }

    private String toJsonString(Object obj) {
        if (obj == null) {
            return "{}"; // 如果对象为null，返回空JSON对象
        }

        try {
            // 使用FastJSON将对象序列化为JSON字符串
            return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error("对象序列化为JSON时出错: {}", e.getMessage());
            return "{}"; // 发生异常时返回空JSON对象
        }
    }
}