/**
 * 
 */
package com.caiyuna.witness.im;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class GroupEventExecutor implements EventExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupEventExecutor.class);

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#isShuttingDown()
     */
    @Override
    public boolean isShuttingDown() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#shutdownGracefully()
     */
    @Override
    public Future<?> shutdownGracefully() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param quietPeriod
     * @param timeout
     * @param unit
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#shutdownGracefully(long, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#terminationFuture()
     */
    @Override
    public Future<?> terminationFuture() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @deprecated
     * @see io.netty.util.concurrent.EventExecutorGroup#shutdown()
     */
    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @deprecated
     * @see io.netty.util.concurrent.EventExecutorGroup#shutdownNow()
     */
    @Override
    public List<Runnable> shutdownNow() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#iterator()
     */
    @Override
    public Iterator<EventExecutor> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param task
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#submit(java.lang.Runnable)
     */
    @Override
    public Future<?> submit(Runnable task) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param task
     * @param result
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#submit(java.lang.Runnable, java.lang.Object)
     */
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param task
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#submit(java.util.concurrent.Callable)
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param command
     * @param delay
     * @param unit
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#schedule(java.lang.Runnable, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param callable
     * @param delay
     * @param unit
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#schedule(java.util.concurrent.Callable, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param command
     * @param initialDelay
     * @param period
     * @param unit
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#scheduleAtFixedRate(java.lang.Runnable, long, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param command
     * @param initialDelay
     * @param delay
     * @param unit
     * @return
     * @see io.netty.util.concurrent.EventExecutorGroup#scheduleWithFixedDelay(java.lang.Runnable, long, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see java.util.concurrent.ExecutorService#isShutdown()
     */
    @Override
    public boolean isShutdown() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see java.util.concurrent.ExecutorService#isTerminated()
     */
    @Override
    public boolean isTerminated() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @see java.util.concurrent.ExecutorService#awaitTermination(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param tasks
     * @return
     * @throws InterruptedException
     * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection)
     */
    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param tasks
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param tasks
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection)
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param tasks
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param command
     * @see java.util.concurrent.Executor#execute(java.lang.Runnable)
     */
    @Override
    public void execute(Runnable command) {
        LOGGER.info("执行命令 command");

    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutor#next()
     */
    @Override
    public EventExecutor next() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutor#parent()
     */
    @Override
    public EventExecutorGroup parent() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutor#inEventLoop()
     */
    @Override
    public boolean inEventLoop() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param thread
     * @return
     * @see io.netty.util.concurrent.EventExecutor#inEventLoop(java.lang.Thread)
     */
    @Override
    public boolean inEventLoop(Thread thread) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutor#newPromise()
     */
    @Override
    public <V> Promise<V> newPromise() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @return
     * @see io.netty.util.concurrent.EventExecutor#newProgressivePromise()
     */
    @Override
    public <V> ProgressivePromise<V> newProgressivePromise() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param result
     * @return
     * @see io.netty.util.concurrent.EventExecutor#newSucceededFuture(java.lang.Object)
     */
    @Override
    public <V> Future<V> newSucceededFuture(V result) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月28日
     * @since 1.0.0
     * @param cause
     * @return
     * @see io.netty.util.concurrent.EventExecutor#newFailedFuture(java.lang.Throwable)
     */
    @Override
    public <V> Future<V> newFailedFuture(Throwable cause) {
        // TODO Auto-generated method stub
        return null;
    }

}
