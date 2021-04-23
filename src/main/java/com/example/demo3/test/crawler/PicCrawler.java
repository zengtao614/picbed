package com.example.demo3.test.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.RedisUtil;
import com.example.demo3.test.util.SpiderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executor;


/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-8
 * @Content:新的爬虫类,集成所有的爬虫方法
 */
@Component
public class PicCrawler {
    @Autowired
    @Lazy
    private IPicInstanceService picInstanceService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    /**
     * 微博爬虫方法
     *
     * @param url
     * @param containerid
     * @param typecode
     * @param sourcecode
     */
    @Async("taskExecutor")
    public void wbspiderRun(String url, String containerid, String typecode, String sourcecode) {
        String content = SpiderUtil.getResponse(url);
        if (content!=null) {
            JSONObject jsonObject = JSON.parseObject(content);
            if ("1".equals(String.valueOf(jsonObject.get("ok")))) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("cards");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONArray picsarray = jsonArray.getJSONObject(i).getJSONObject("mblog").getJSONArray("pics");
                    if (picsarray != null) {
                        for (int n = 0; n < picsarray.size(); n++) {
                            String picurl = (String) picsarray.getJSONObject(n).getJSONObject("large").get("url");
                            String picname = picurl.substring(picurl.lastIndexOf("/") + 1);
                            saveImg(picname, sourcecode, typecode, containerid, picurl);
                        }
                    }
                }
            }
        }
    }

    /**
     * 测试方法
     * @param url
     * @param containerid
     * @param typecode
     * @param sourcecode
     * @param page
     */
    @Async("taskExecutor")
    public void wbspiderRun(String url, String containerid, String typecode, String sourcecode,int page) {
        System.out.println("线程启动，当前url:"+url);
        String content = SpiderUtil.getResponse(url);
        if (content!=null) {
            JSONObject jsonObject = JSON.parseObject(content);
            if ("1".equals(String.valueOf(jsonObject.get("ok")))) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("cards");
                redisUtil.setString(containerid+":"+page+"_count",String.valueOf(jsonArray.size()));
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (jsonArray.getJSONObject(i).getJSONObject("mblog")!=null) {
                        JSONArray picsarray = jsonArray.getJSONObject(i).getJSONObject("mblog").getJSONArray("pics");
                        if (picsarray != null) {
                            for (int n = 0; n < picsarray.size(); n++) {
                                String picurl = (String) picsarray.getJSONObject(n).getJSONObject("large").get("url");
                                String picname = picurl.substring(picurl.lastIndexOf("/") + 1);
                                //saveImg(picname, sourcecode, typecode, containerid, picurl);
                                saveImgTest(picname, sourcecode, typecode, containerid, picurl);
                            }
                        }
                    }
                    redisUtil.setString(containerid+":"+page+"_nownum",String.valueOf(i+1));
                }
            }else {
                redisUtil.setString(containerid+":"+page+"_count",String.valueOf(0));
                redisUtil.setString(containerid+":"+page+"_nownum",String.valueOf(0));
            }
        }
        System.out.println("第"+page+"已爬取完毕");
        redisUtil.setIntAdd(containerid+":allpage");
    }

    /**
     * 花瓣网爬虫方法
     *
     * @param boardid
     * @param typecode
     * @param sourcecode
     */
    @Async("taskExecutor")
    public void hbspiderRun(String boardid, String typecode, String sourcecode) {
        try {
            SpiderUtil.createFolder(SpiderUtil.folder_name + boardid);
            String url = SpiderUtil.HUABAN_BOARDSITE + boardid;
            String thisUrl = url;
            while (true) {
                StringBuffer html = SpiderUtil.getHtml(thisUrl);
                LinkedHashSet<String> srcSet = SpiderUtil.getSrc(html);
                if (srcSet.size() == 0) {
                    System.out.println(url + "页面已爬取完成");
                    break;
                }
                for (String src : srcSet) {
                    String picname = src.substring(src.lastIndexOf("/") + 1) + ".jpg";
                    saveImg(picname, sourcecode, typecode, boardid, src);
                }
                thisUrl = SpiderUtil.getNextUrl(html, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储图片到数据库和本地
     * @param picname
     * @param sourcecode
     * @param typecode
     * @param folderid
     * @param picurl
     */
    public void saveImg(String picname, String sourcecode, String typecode, String folderid, String picurl) {
        try {
            if (picInstanceService != null) {
                //数据库存储操作
                PicInstance picInstance = new PicInstance();
                picInstance.setId(UUID.randomUUID().toString());
                picInstance.setPicName(picname);
                picInstance.setPicSource(Integer.valueOf(sourcecode));
                picInstance.setPicTypecode(Integer.valueOf(typecode));
                picInstance.setPicUrl(folderid + "/" + picInstance.getPicName());
                picInstance.setPicOriurl(picurl);
                picInstance.setPicSavedate(new Date());
                picInstanceService.insert(picInstance);
                //下载图片到本地
                SpiderUtil.downloadpic(picurl, SpiderUtil.folder_name + folderid, picname);
                System.out.println(picname + "保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(picname + "保存失败");
        }
    }

    /**
     * 模拟存储图片，删去下载操作，节省流量
     * @param picname
     * @param sourcecode
     * @param typecode
     * @param folderid
     * @param picurl
     */
    public void saveImgTest(String picname, String sourcecode, String typecode, String folderid, String picurl) {
        try {
            if (picInstanceService != null) {
                //数据库存储操作
                PicInstance picInstance = new PicInstance();
                picInstance.setId(UUID.randomUUID().toString());
                picInstance.setPicName(picname);
                picInstance.setPicSource(Integer.valueOf(sourcecode));
                picInstance.setPicTypecode(Integer.valueOf(typecode));
                picInstance.setPicUrl(folderid + "/" + picInstance.getPicName());
                picInstance.setPicOriurl(picurl);
                picInstance.setPicSavedate(new Date());
                picInstanceService.insert(picInstance);
            }
            //Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(picname + "保存失败");
        }
    }
    /**
     * 模拟存储图片，删去下载操作，节省流量
     * @param picname
     * @param sourcecode
     * @param typecode
     * @param folderid
     * @param picurl
     */
    public void saveImgTest2(String picname, String sourcecode, String typecode, String folderid, String picurl) {
        try {
            //下载图片到本地
            SpiderUtil.downloadpic(picurl, SpiderUtil.folder_name + folderid, picname);
            System.out.println(picname + "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(picname + "保存失败");
        }
    }

    public Map shutdownThreadPool() {
        Map map = new HashMap();
        map.put("总任务数",taskExecutor.getThreadPoolExecutor().getTaskCount());
        map.put("还剩任务数",taskExecutor.getThreadPoolExecutor().getQueue().size());
        map.put("完成任务数",taskExecutor.getThreadPoolExecutor().getCompletedTaskCount());
        return map;
    }
}
