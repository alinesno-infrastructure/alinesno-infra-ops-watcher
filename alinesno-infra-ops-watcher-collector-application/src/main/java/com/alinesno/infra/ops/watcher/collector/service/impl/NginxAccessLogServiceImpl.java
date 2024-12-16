package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alinesno.infra.ops.watcher.collector.receiver.bean.NginxAccessLog;
import com.alinesno.infra.ops.watcher.collector.service.INginxAccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Arrays;

@Slf4j
@Service
public class NginxAccessLogServiceImpl implements INginxAccessLogService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveNginxAccessLogs(NginxAccessLog nginxLog) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();
            String insertSql = "INSERT INTO nginx_access_log (" +
                    "remote_addr, " +
                    "remote_user, " +
                    "time_local, " +
                    "request, " +
                    "status, " +
                    "body_size, " +
                    "referer, " +
                    "agent, " +
                    "x_forwarded, " +
                    "request_time, " +
                    "upstream_response_time, " +
                    "upstream_addr, " +
                    "ssl_protocol, " +
                    "ssl_cipher, " +
                    "request_length, " +
                    "gzip_ratio, " +
                    "connection, " +
                    "connection_requests, " +
                    "msec, " +
                    "pipe, " +
                    "server_protocol, " +
                    "http_host, " +
                    "http_cookie, " +
                    "bytes_sent, " +
                    "request_method, " +
                    "scheme, " +
                    "request_uri, " +
                    "args, " +
                    "http_accept_encoding, " +
                    "http_accept_language, " +
                    "http_via, " +
                    "http_connection, " +
                    "http_cache_control) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    "?,?,?)";

            statement = connection.prepareStatement(insertSql);


            statement = connection.prepareStatement(insertSql);

            // 使用特定类型的setter方法设置参数
            statement.setString(1, nginxLog.getRemoteAddr());
            statement.setString(2, nginxLog.getRemoteUser());
            statement.setString(3, nginxLog.getTimeLocal());
            statement.setString(4, nginxLog.getRequest());
            statement.setInt(5, parseStatus(nginxLog.getStatus()));
            statement.setInt(6, parseIntSafe(nginxLog.getBodySize()));
            statement.setString(7, nginxLog.getReferer());
            statement.setString(8, nginxLog.getAgent());
            statement.setString(9, nginxLog.getXForwarded());
            statement.setString(10, nginxLog.getRequestTime()); // 如果不存在则为null
            statement.setString(11, nginxLog.getUpstreamResponseTime());
            statement.setString(12, nginxLog.getUpstreamAddr());
            statement.setString(13, nginxLog.getSslProtocol());
            statement.setString(14, nginxLog.getSslCipher());
            statement.setInt(15, parseIntSafe(String.valueOf(nginxLog.getRequestLength())));
            statement.setDouble(16, parseDoubleSafe(nginxLog.getGzipRatio(), 0.0)); // 默认值为"0.0"
            statement.setLong(17, parseLongSafe(String.valueOf(nginxLog.getConnection())));
            statement.setInt(18, parseIntSafe(String.valueOf(nginxLog.getConnectionRequests())));
            statement.setDouble(19, parseDoubleSafe(String.valueOf(nginxLog.getMsec()), 0.0));
            statement.setString(20, nginxLog.getPipe());
            statement.setString(21, nginxLog.getServerProtocol());
            statement.setString(22, nginxLog.getHttpHost());
            statement.setString(23, nginxLog.getHttpCookie());
            statement.setInt(24, parseIntSafe(String.valueOf(nginxLog.getBytesSent())));
            statement.setString(25, nginxLog.getRequestMethod());
            statement.setString(26, nginxLog.getScheme());
            statement.setString(27, nginxLog.getRequestUri());
            statement.setString(28, nginxLog.getArgs());
            statement.setString(29, nginxLog.getHttpAcceptEncoding());
            statement.setString(30, nginxLog.getHttpAcceptLanguage());
            statement.setString(31, nginxLog.getHttpVia());
            statement.setString(32, nginxLog.getHttpConnection());
            statement.setString(33, nginxLog.getHttpCacheControl());

            statement.addBatch();
            int[] result = statement.executeBatch();
            log.debug("批量插入NginxAccessLog结果: {}", Arrays.toString(result));

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

    private Integer parseStatus(String status) {
        return status != null && !"-".equals(status) ? Integer.parseInt(status) : 0;
    }

    private Integer parseIntSafe(String value) {
        return value != null && !"-".equals(value) && StringUtils.isNumeric(value) ? Integer.parseInt(value) : 0;
    }

    private Long parseLongSafe(String value) {
        return value != null && !"-".equals(value) && StringUtils.isNumeric(value) ? Long.parseLong(value) : 0L;
    }

    private Double parseDoubleSafe(String value, double defaultValue) {
        return value != null && !"-".equals(value) && StringUtils.isNumeric(value.replace(",", ".")) ? Double.parseDouble(value) : defaultValue;
    }
}