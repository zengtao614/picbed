package com.example.demo3.test.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.SpiderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.UUID;


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
     * 花瓣网爬虫方法
     *
     * @param boardid
     * @param typecode
     * @param sourcecode
     */
    @Async("taskExecutor")
    public void hbspiderRun(String boardid, String typecode, String sourcecode) {
        try {
            SpiderUtil.createFolder(SpiderUtil.folder_name + boardid + "\\");
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
                picInstanceService.insert(picInstance);
                //下载图片到本地
                SpiderUtil.downloadpic(picurl, SpiderUtil.folder_name + folderid + "\\", picname);
                System.out.println(picname + "保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(picname + "保存失败");
        }
    }


}
