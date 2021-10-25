package org.xl.utils.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author xulei
 */
public class Consumer implements WorkHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent event) throws Exception {
        System.out.println(Thread.currentThread().getName() + ": DataEvent value is " + event.getValue());
    }
}
