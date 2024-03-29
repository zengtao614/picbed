package com.example.demo3.test.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-8
 * @Content: 线程池配置类
 */
@Configuration
@EnableAsync
public class TaskPoolConfig {
    /**
     * 想法是一个单独存数据到数据库的线程池，一个负责下载图片的线程
     * @return
     */

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutro(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("taskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
    }

    /**
     * 下载线程，同时只允许3个子线程进行工作
     * @return
     */
    @Bean("downloadTaskExecutor")
    public ThreadPoolTaskExecutor downloadTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.setThreadNamePrefix("downloadTaskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(30);
        return taskExecutor;
    }


}
