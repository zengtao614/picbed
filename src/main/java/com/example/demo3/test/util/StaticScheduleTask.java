package com.example.demo3.test.util;

import com.example.demo3.test.crawler.PicCrawler;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.service.IPicInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
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


    /**
     * 定时下载未下载的图片
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    /*@Scheduled(fixedRate=40000)
    private void downloadPic() {
        System.out.println("定时下载图片任务开始");
        List<PicInstance> picList = picInstanceService.getNeeddownpicTest();
        for (PicInstance p:picList){
            try {
                SpiderUtil.downloadpic(p.getPicOriurl(),SpiderUtil.folder_name + p.getPicUrl());
                p.setPicHasdown(1);
                picInstanceService.updatePicinstance(p);
                System.out.println(p.getPicName() + "下载成功");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(p.getPicName() + "下载失败");
            }

        }
        System.out.println("定时下载图片任务结束");
    }*/
}
