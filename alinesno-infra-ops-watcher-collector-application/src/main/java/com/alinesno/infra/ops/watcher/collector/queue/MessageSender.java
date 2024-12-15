//package com.alinesno.infra.ops.watcher.collector.queue;
//
//import com.lmax.disruptor.RingBuffer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageSender {
//
//    private final RingBuffer<MessageEvent> ringBuffer;
//
//    public MessageSender(RingBuffer<MessageEvent> ringBuffer) {
//        this.ringBuffer = ringBuffer;
//    }
//
//    public void sendMessage(String topic , String message) {
//        long sequence = ringBuffer.next();  // 获取下一个可用序列号
//        try {
//            MessageEvent event = ringBuffer.get(sequence); // 获取该序列号对应的事件
//            event.setMessage(message); // 设置事件数据
//            event.setTopic(topic);
//        } finally {
//            ringBuffer.publish(sequence); // 发布事件
//        }
//    }
//}