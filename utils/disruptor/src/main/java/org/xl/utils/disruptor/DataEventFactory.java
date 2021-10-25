package org.xl.utils.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author xulei
 */
public class DataEventFactory implements EventFactory<DataEvent> {

    @Override
    public DataEvent newInstance() {
        return new DataEvent();
    }
}
