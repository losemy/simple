package com.github.losemy.simple.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author lose
 * @date 2019-10-17
 * 监控线程运行状态
 **/
@Slf4j
public class MonitorThreadPoolExecutor extends ThreadPoolExecutor {

    private String poolName;

    /**
     * 是否需要其他参数
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     * @param poolName
     */
    public MonitorThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler,String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        Assert.notNull(poolName,"线程池名称不能为空");
        this.poolName = poolName;
    }

    private void monitorThreadPoolInfo(){
        log.info("{},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.poolName, this.getTaskCount(),
                this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
    }


    @Override
    public void execute(Runnable command) {
        monitorThreadPoolInfo();
        super.execute(command);
    }

    @Override
    public Future<?> submit(Runnable task) {
        monitorThreadPoolInfo();
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        monitorThreadPoolInfo();
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        monitorThreadPoolInfo();
        return super.submit(task, result);
    }

    @Override
    public List<Runnable> shutdownNow() {
        monitorThreadPoolInfo();
        return super.shutdownNow();
    }

    @Override
    public void shutdown() {
        monitorThreadPoolInfo();
        super.shutdown();
    }

}
