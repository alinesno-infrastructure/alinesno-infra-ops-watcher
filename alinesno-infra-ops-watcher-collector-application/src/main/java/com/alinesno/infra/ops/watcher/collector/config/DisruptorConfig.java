//package com.alinesno.infra.ops.watcher.collector.config;
//
//import com.alinesno.infra.ops.watcher.collector.queue.MessageEvent;
//import com.alinesno.infra.ops.watcher.collector.queue.MessageEventFactory;
//import com.alinesno.infra.ops.watcher.collector.service.impl.MessageReceiver;
//import com.lmax.disruptor.RingBuffer;
//import com.lmax.disruptor.dsl.Disruptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Configuration
//public class DisruptorConfig {
//
//    private final ExecutorService executor = Executors.newCachedThreadPool();
//
//    @Bean
//    public Disruptor<MessageEvent> disruptor() {
//        int bufferSize = 1024; // 缓冲区大小，必须是2的幂
//        Disruptor<MessageEvent> disruptor = new Disruptor<>(new MessageEventFactory(), bufferSize, executor);
//        disruptor.handleEventsWith(new MessageReceiver()); // 使用MessageReceiver处理事件
//        disruptor.start();
//        return disruptor;
//    }
//
//    @Bean
//    public RingBuffer<MessageEvent> ringBuffer(Disruptor<MessageEvent> disruptor) {
//        return disruptor.getRingBuffer();
//    }
//}