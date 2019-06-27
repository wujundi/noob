package com.noob.spider.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread pool for workers.<br><br>
 * Use {@link java.util.concurrent.ExecutorService} as inner implement. <br><br>
 * New feature: <br><br>
 * 1. Block when thread pool is full to avoid poll many urls without process. <br><br>
 * 2. Count of thread alive for monitor.
 *
 * Spider 类“御用”的多线程方案
 *
 * @author code4crafer@gmail.com
 * @since 0.5.0
 */
public class CountableThreadPool {

    private int threadNum;

    /**
     * 用一个线程安全的原子整型来表示存活的线程数
     */
    private AtomicInteger threadAlive = new AtomicInteger();

    private ReentrantLock reentrantLock = new ReentrantLock();

    private Condition condition = reentrantLock.newCondition();

    public CountableThreadPool(int threadNum) {
        this.threadNum = threadNum;
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    public CountableThreadPool(int threadNum, ExecutorService executorService) {
        this.threadNum = threadNum;
        this.executorService = executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int getThreadAlive() {
        return threadAlive.get();
    }

    public int getThreadNum() {
        return threadNum;
    }

    private ExecutorService executorService;

    public void execute(final Runnable runnable) {

        if (threadAlive.get() >= threadNum) {
            try {
                // 获取锁
                reentrantLock.lock();
                // 如果正在运行的线程数threadAlive超过允许的线程数，就阻塞等待，直至收到某个线程结束通知
                while (threadAlive.get() >= threadNum) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        // 对于原子整型进行CAS操作
        threadAlive.incrementAndGet();
        // 2019-06-27 18:05 下面这里每太看懂
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {
                    try {
                        reentrantLock.lock();
                        threadAlive.decrementAndGet();
                        condition.signal();
                    } finally {
                        reentrantLock.unlock();
                    }
                }
            }
        });
    }

    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    public void shutdown() {
        executorService.shutdown();
    }


}

// 2019-06-27 18:06