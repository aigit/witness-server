package com.caiyuna.witness.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池实现
 * @author Gaoll
 * @since HZD-1.0.0
 */
public final class ThreadPoolUtil {

    // 线程池维护线程的最少数量
    private static final int COREPOOLSIZE = 5;
    // 线程池维护线程的最大数量
    private static final int MAXINUMPOOLSIZE = 20;
    // 线程池维护线程所允许的空闲时间
    private static final long KEEPALIVETIME = 5;
    // 线程池维护线程所允许的空闲时间的单位
    private static final TimeUnit UNIT = TimeUnit.MINUTES;
    // 线程池所使用的缓冲队列
    private static final BlockingQueue<Runnable> WORKQUEUE = new ArrayBlockingQueue<>(100);
    // 线程池对拒绝任务的处理策略：
    /*
     * AbortPolicy为抛出异常
     * CallerRunsPolicy为重试添加当前的任务，自动重复调用execute()方法
     * DiscardOldestPolicy为抛弃旧的任务
     * DiscardPolicy为抛弃当前的任务
     */
    private static final CallerRunsPolicy HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(COREPOOLSIZE, MAXINUMPOOLSIZE, KEEPALIVETIME, UNIT, WORKQUEUE, HANDLER);

    public static void execute(Runnable r) {
        executor.execute(r);
    }

    public static boolean isShutDown() {
        return executor.isShutdown();
    }

    public static void shutDownNow() {
        executor.shutdownNow();
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
