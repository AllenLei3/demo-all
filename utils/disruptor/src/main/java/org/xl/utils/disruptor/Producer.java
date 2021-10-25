package org.xl.utils.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.util.Random;

/**
 * @author xulei
 */
public class Producer {

    private final RingBuffer<DataEvent> ringBuffer;

    public Producer(RingBuffer<DataEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publishDataEvent() {
        long sequence = ringBuffer.next();
        try {
            // 复用DataEvent
            DataEvent dataEvent = ringBuffer.get(sequence);
            dataEvent.setValue(new Random().nextInt(100));
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
