package com.example.demo3.test.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.SpiderUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.UUID;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-1
 * @Content:
 */
public class WeiboSpider extends Spider{
    private static String baseurl = "https://m.weibo.cn/api/container/getIndex?containerid=";

    private String containerid;

    public WeiboSpider(String containerid, String typecode, String sourcecode, IPicInstanceService picInstanceService){
        this.containerid = containerid;
        this.url = baseurl + containerid;
        this.typecode = typecode;
        this.sourcecode = sourcecode;
        this.picInstanceService = picInstanceService;
    }


    @Override
    public void run() {
        try {
            File file = new File(SpiderUtil.folder_name + containerid + "\\");
            if (file.exists()) {
                SpiderUtil.delete(file);
            }
            file.mkdirs();
            JSONObject pagejson = JSON.parseObject(SpiderUtil.getResponse(url));
            if("1".equals(String.valueOf(pagejson.get("ok")))){
                int total = (Integer)(pagejson.getJSONObject("data").getJSONObject("cardlistInfo").get("total"));
                int page = (total/10) + 1;
                for (int i=0;i<=page;i++){
                    spiderRun(url + "&page=" + i);
                }
            }else{
                System.out.println("此用户下无微博！");
            }


            //spiderRun(thisUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spiderRun(String url){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try{
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity!=null) {
                JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity));
                if (response.getStatusLine().getStatusCode() == 200 && "1".equals(String.valueOf(jsonObject.get("ok")))) {
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("cards");
                    for (int i=0;i<jsonArray.size();i++){
                        JSONArray picsarray = jsonArray.getJSONObject(i).getJSONObject("mblog").getJSONArray("pics");
                        if(picsarray!=null){
                            for (int n=0;n<picsarray.size();n++){
                                String picurl = (String) picsarray.getJSONObject(n).getJSONObject("large").get("url");
                                String picname = picurl.substring(picurl.lastIndexOf("/") + 1);
                                try {
                                    if (picInstanceService != null) {
                                        //数据库存储操作
                                        PicInstance picInstance = new PicInstance();
                                        picInstance.setId(UUID.randomUUID().toString());
                                        picInstance.setPicName(picname);
                                        picInstance.setPicSource(Integer.valueOf(sourcecode));
                                        picInstance.setPicTypecode(Integer.valueOf(typecode));
                                        picInstance.setPicUrl(containerid + "/" + picInstance.getPicName());
                                        picInstanceService.insert(picInstance);
                                        //下载图片到本地
                                        SpiderUtil.downloadpic(picurl,SpiderUtil.folder_name + containerid + "\\",picname);
                                        System.out.println(picname + "保存成功");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println(picname + "保存失败");
                                }

                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response!=null){
                    response.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
