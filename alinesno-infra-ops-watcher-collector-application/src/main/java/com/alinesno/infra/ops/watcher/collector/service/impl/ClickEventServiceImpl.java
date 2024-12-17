package com.alinesno.infra.ops.watcher.collector.service.impl;

import com.alinesno.infra.ops.watcher.collector.bean.ClickLogEntry;
import com.alinesno.infra.ops.watcher.collector.service.IClickEventService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class ClickEventServiceImpl implements IClickEventService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveClickEvents(List<ClickLogEntry> clickLogList, String uid, String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sqlSessionTemplate.getConnection();
            String insertSql = "INSERT INTO font_click_events (" +
                    "log_type, " +
                    "ele_type, " +
                    "ele_content, " +
                    "time_step, " +
                    "href, " +
                    "ele_location, " +
                    "uid, " +
                    "id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(insertSql);

            for(ClickLogEntry clickLog : clickLogList){
                // 使用特定类型的setter方法设置参数
                statement.setString(1, clickLog.getLogType());
                statement.setString(2, clickLog.getEleType());
                statement.setString(3, clickLog.getEleContent());
                statement.setLong(4, clickLog.getTimeStep());
                statement.setString(5, clickLog.getHref());
                statement.setString(6, clickLog.getEleLocation());
                statement.setString(7, uid);
                statement.setString(8, id);

                int result = statement.executeUpdate();
                log.debug("插入ClickLog结果: {}", result);
            }


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
}