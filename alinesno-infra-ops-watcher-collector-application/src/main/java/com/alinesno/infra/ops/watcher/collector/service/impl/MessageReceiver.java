//package com.alinesno.infra.ops.watcher.collector.service.impl;
//
//import com.alinesno.infra.ops.watcher.collector.constants.Constants;
//import com.alinesno.infra.ops.watcher.collector.queue.MessageEvent;
//import com.alinesno.infra.ops.watcher.collector.service.BaseLogCollect;
//import com.lmax.disruptor.EventHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//public class MessageReceiver extends BaseLogCollect implements EventHandler<MessageEvent> {
//
//    @Override
//    public void onEvent(MessageEvent event, long sequence, boolean endOfBatch) throws Exception {
//        // 处理接收到的消息
//        log.debug("-->>> Received topic: {}", event.getTopic());
//        log.debug("-->>> Received message: {}", event.getMessage());
//
//        List<String> logList = new ArrayList<>();
//        List<String> metricsList = new ArrayList<>();
//        List<String> traceList = new ArrayList<>();
//
//        switch (event.getTopic()) {
//            case Constants.MQ_METRICS_TOPIC -> {
//                String logString = event.getMessage();
//                metricsList.add(logString);
//
//            }
//            case Constants.MQ_LOG_TOPIC -> {
//                String logString = event.getMessage();
//                logList.add(logString);
//            }
//            case Constants.MQ_TRACE_TOPIC -> {
//                String logString = event.getMessage();
//                traceList.add(logString);
//            }
//        }
//
//        // 链路跟踪记录
//        if (!traceList.isEmpty()) {
//            super.handleTrace(traceList);
//        }
//
//        // 监控记录
//        if (!metricsList.isEmpty()) {
//            super.handleMetrics(metricsList);
//        }
//
//        // 日志记录
//        if (!logList.isEmpty()) {
//            super.handleLog(logList);
//        }
//
//    }
//}