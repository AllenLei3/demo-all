package org.xl.utils.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * @author xulei
 */
public class Main {

    public static void main(String[] args) {
        // 指定RingBuffer大小,必须是2的N次方
        int bufferSize = 1024;
        // 构建Disruptor
        Disruptor<DataEvent> disruptor = new Disruptor<> (new DataEventFactory(),
                bufferSize,
                DaemonThreadFactory.INSTANCE,
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        // 注册事件处理器
        disruptor.handleEventsWithWorkerPool(new Consumer());
        // 启动Disruptor
        disruptor.start();
        // 获取RingBuffer
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        // 生产Event
        Producer producer = new Producer(ringBuffer);
        for (long i = 0; i < 10; i++) {
            // 生产者生产消息
            producer.publishDataEvent();
        }
    }
}
