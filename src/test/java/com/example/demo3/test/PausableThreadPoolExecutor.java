package com.example.demo3.test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-19
 * @Content:
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor {



    boolean isPause = false;
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    @Override
    public void beforeExecute(Thread t, Runnable r) {
        lock.lock();
        super.beforeExecute(t,r);
        try {
            while (isPause){
                long ms = 10L;
                System.out.printf("pausing, %s\n", t.getName());
                Thread.sleep(ms);
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 暂停
     */
    public void pause(){
        lock.lock();
        System.out.println("exe pause");
        isPause = true;
        lock.unlock();
    }

    /**
     * 继续执行
     */
    public void resume(){
        lock.lock();
        System.out.println("un pause");
        isPause = false;
        condition.signalAll();
        lock.unlock();
    }
}