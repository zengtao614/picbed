package com.example.demo3.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-19
 * @Content:
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void threadTest(){
        /*PausableThreadPoolExecutor poolExecutor =
                new PausableThreadPoolExecutor(10, 10, 1000L,
                        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());*/
        PausableThreadPoolExecutor poolExecutor = new PausableThreadPoolExecutor(10, 10, 1000L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            final int b = i;
            if (b==25){
                System.out.println("暂停");
                poolExecutor.shutdown();
                        /*Thread.sleep(5000);
                        poolExecutor.resume();*/
            }
            poolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程开始");
                try {

                    for (int c = 0; c < 10; c++) {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + ":" + c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程结束");
            });
        }


    }
}
