package org.xl.kafka.safe;

/**
 * @author xulei
 */
public class PartitionOffset {

    private final int partition;
    private final long offset;

    public PartitionOffset(int partition, long offset) {
        this.partition = partition;
        this.offset = offset;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }
}
