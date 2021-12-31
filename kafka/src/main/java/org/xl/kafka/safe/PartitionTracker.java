package org.xl.kafka.safe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 主题中单个分区的追踪器。当{@link OffsetTracker}开始跟踪该分区的第一个偏移量的时候，
 * 将为每个分区创建一个此类的实例。
 *
 * @author xulei
 */
public class PartitionTracker {

    private static final Logger LOG = LoggerFactory.getLogger(PartitionTracker.class);

    private final Map<Long /*page index*/, PageTracker> pageTrackers = new HashMap<>();
    private final SortedSet<Long /*page index*/> completedPages = new TreeSet<>();
    private long lastConsecutivePageIndex;
    private final int pageSize;
    private final int maxOpenPagesPerPartition;

    public PartitionTracker(long offset, int pageSize, int maxOpenPagesPerPartition) {
        this.pageSize = pageSize;
        this.maxOpenPagesPerPartition = maxOpenPagesPerPartition;
        lastConsecutivePageIndex = offsetToPage(offset) - 1;
    }

    public Boolean track(long offset) {
        long pageIndex = offsetToPage(offset);
        int margin = (int) (offset % pageSize);
        if (pageTrackers.get(pageIndex) == null) {
            if (pageTrackers.size() >= maxOpenPagesPerPartition) {
                LOG.warn("page num 已经超出阈值!");
                return false;
            }
            pageTrackers.put(pageIndex, new PageTracker(pageSize, margin));
        }
        return true;
    }

    /**
     * 如果没有可以在此分区上安全提交的新偏移量则为空，如果存在则为提交的安全偏移量。
     *
     * @param offset 确认的偏移量
     */
    public OptionalLong ack(long offset) {
        // 告诉相应的页面跟踪器已确认此偏移量。
        long pageIndex = offsetToPage(offset);
        PageTracker pageTracker = pageTrackers.get(pageIndex);
        if (pageTracker == null) {
            LOG.warn("接收到未跟踪区域的位移提交！offset:{}", offset);
            return OptionalLong.empty();
        }
        int pageOffset = (int) (offset % pageSize);
        if (!pageTracker.ack(pageOffset)) {
            return OptionalLong.empty();
        }
        // 如果该页面上的所有偏移量都已被确认，则将页面添加到已完成页面的列表中。
        pageTrackers.remove(pageIndex);
        if (pageIndex <= lastConsecutivePageIndex) {
            LOG.warn("接收到页面已跟踪完成的确认，但该页面已经确认过！offset:{}, completed page index:{}", offset, pageIndex);
            return OptionalLong.empty();
        }
        completedPages.add(pageIndex);

        // See whether the completed pages, construct a consecutive chain.
        int numConsecutive = 0;
        Iterator<Long> iterator = completedPages.iterator();
        while (iterator.hasNext()) {
            long index = iterator.next();
            if (index != lastConsecutivePageIndex + 1) {
                break;
            }
            numConsecutive++;
            lastConsecutivePageIndex = index;
        }

        // 没有连续的完成页面。因此，没有偏移量可以安全提交。
        if (numConsecutive == 0) {
            return OptionalLong.empty();
        }
        // 有连续完成的页面，提交并删除
        iterator = completedPages.iterator();
        for (int i = 0; i < numConsecutive; i++) {
            iterator.next();
            iterator.remove();
        }
        return OptionalLong.of(pageToFirstOffset(lastConsecutivePageIndex + 1));
    }

    public long drainNotFullOffset() {
        // 如果有可以提交的页，则不处理
        if (completedPages.size() != 0) {
            return -1;
        }
        Set<Long> pageIndex = this.pageTrackers.keySet();
        long minPageIndex = -1;
        for (Long index : pageIndex) {
            if (index > minPageIndex) {
                minPageIndex = index;
            }
        }
        PageTracker pageTracker = this.pageTrackers.get(minPageIndex);
        if (pageTracker != null) {
            // 返回最小可以提交的偏移量
            return pageTracker.drainNotFullOffset() + (minPageIndex * pageSize);
        }
        return -1;
    }

    private long pageToFirstOffset(long pageIndex) {
        return pageIndex * pageSize;
    }

    private long offsetToPage(long offset) {
        return offset / pageSize;
    }
}
