package com.example.demo3.test;

import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private TestService testService;


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
            poolExecutor.execute(() -> {

            });
        }


    }
}
