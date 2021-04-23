package com.example.demo3.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-23
 * @Content:
 */
@SpringBootTest
public class TestService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public synchronized void add(int b){
        try {
            File file = new File("D://test.json");
            Integer num = Integer.valueOf(stringRedisTemplate.opsForValue().get("num"));
            System.out.println(Thread.currentThread().getName()+"====="+b+"========现在的num是:"+num);
            stringRedisTemplate.opsForValue().set("num",String.valueOf(num + 1));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void threadTest(){
        stringRedisTemplate.opsForValue().set("num",String.valueOf(0));
        ThreadPoolExecutor poolExecutor =
                new ThreadPoolExecutor(10, 10, 1000L,
                        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            final int b = i;
            poolExecutor.execute(() -> {
                add(b);
            });
        }


    }

}
