package org.xl.kafka.safe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;

/**
 * 分区中单个页面的跟踪器。当{@link PartitionTracker}开始跟踪该页面的第一个偏移量时，
 * 将为每个页面创建此类的一个实例。
 *
 * @author xulei
 */
public class PageTracker {

    private static final Logger LOG = LoggerFactory.getLogger(PageTracker.class);

    private final int effectiveSize;
    private final int margin;
    private final BitSet bits;
    private int numConsecutive;

    public PageTracker(int size, int margin) {
        this.effectiveSize = size - margin;
        this.margin = margin;
        this.numConsecutive = 0;
        bits = new BitSet(effectiveSize);
    }

    /**
     * 确认指定的偏移量，如果该页面的所有预期的偏移量都已确认，则返回true
     *
     * @param offset 确认的偏移量
     */
    public Boolean ack(int offset) {
        if (offset < margin) {
            LOG.warn("接收到未跟踪区域的位移提交！offset:{}, page margin:{}", offset, margin);
            return false;
        }
        // 设置代表该偏移量的位
        int effectiveOffset = offset - margin;
        bits.set(effectiveOffset);
        // 查找从边距开始确认的连续偏移量。
        if (effectiveOffset == numConsecutive) {
            numConsecutive++;
            while (bits.get(++effectiveOffset) && numConsecutive < this.effectiveSize) {
                numConsecutive++;
            }
        }
        return (numConsecutive == effectiveSize);
    }

    public long drainNotFullOffset() {
        if (this.numConsecutive == 0) {
            return -1;
        }
        // 从边距开始取连续确认的偏移量,注意bits索引从0开始的，margin从0开始的，但返回的是该页可以提交的数量
        int start = this.margin;
        while (bits.get(start)) {
            start++;
        }
        return start;
    }
}
