package org.xl.java.concurrence;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author xulei
 */
public class ForkJoinDemo {

    public static void directInvoke() throws ExecutionException, InterruptedException {
        // 创建一个计算任务，计算由1加到12
        CountTask countTask2 = new CountTask(1, 12);
        // 直接在main线程中调用 fork 来提交任务,
        countTask2.fork();
        // 没有创建线程池，使用的commonPool线程池
        countTask2.get();
    }

    public static class CountTask extends RecursiveTask<Integer> {
        // 任务最小分割的阈值
        private static final int THRESHOLD = 2;

        private final int start;
        private final int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                // 如果任务足够小就计算任务
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                // 如果任务大于阈值，就分割成两个子任务进行计算
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                // 执行子任务
                leftTask.fork();
                rightTask.fork();
                // 等待子任务执行完成
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();
                sum = leftResult + rightResult;
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 4);
        // 提交任务
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            result.get();
        } catch (Exception e){
            // ignore
        }
    }
}
