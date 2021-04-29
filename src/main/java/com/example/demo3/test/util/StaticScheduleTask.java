package com.example.demo3.test.util;

import com.example.demo3.test.crawler.PicCrawler;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.service.IPicInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-23
 * @Content:
 */

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class StaticScheduleTask {

    @Autowired
    private IPicInstanceService picInstanceService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 定时下载未下载的图片每晚下载1000条
     */
    @Scheduled(cron = "0 0 0 ? * 2-7")//除了每周周日外每天晚上12点自动执行图片下载方法
    private void downloadPic() {
        System.out.println("定时下载图片任务开始，当前时间为:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        redisUtil.setInt("downloadStatus", 0);
        List<PicInstance> picList = picInstanceService.getNeeddownpicTest();
        int successDown = 0;
        int failDown = 0;
        for (PicInstance p:picList){
            try {
                SpiderUtil.downloadpic(p.getPicOriurl(),SpiderUtil.folder_name + p.getPicUrl());
                p.setPicHasdown(1);
                picInstanceService.updatePicinstance(p);
                successDown += 1;
                System.out.println(p.getPicName() + "下载成功");
            }catch (Exception e){
                e.printStackTrace();
                failDown += 1;
                System.out.println(p.getPicName() + "下载失败");
            }

        }
        redisUtil.setInt("downloadStatus", 0);
        System.out.println("定时下载图片任务结束,当前时间为:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+";下载成功"+successDown+"张，下载失败"+failDown+"张。");
    }
}
