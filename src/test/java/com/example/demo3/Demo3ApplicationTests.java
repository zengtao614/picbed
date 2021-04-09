package com.example.demo3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.test.util.SpiderUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.*;

@SpringBootTest
class Demo3ApplicationTests {



/*    @Test
    void contextLoads() {
        String picur = "https://wx3.sinaimg.cn/large/e9850d5agy1flkww6brgij215o15ox6p.jpg";
        System.out.println(picur.substring(picur.lastIndexOf("/") + 1));

    }*/

    /*    @Test
        void httpTest(){
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("https://m.weibo.cn/api/container/getIndex?containerid=1076033917811034");
            CloseableHttpResponse response = null;
            try{
                response = httpClient.execute(httpGet);
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity!=null) {
                    JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity));
                    if (response.getStatusLine().getStatusCode() == 200 && String.valueOf(jsonObject.get("ok")).equals("1")) {
                        System.out.println(jsonObject.getJSONObject("data").getJSONObject("cardlistInfo").get("total"));
                        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("cards");
                        for (int i=0;i<jsonArray.size();i++){
                            JSONArray picsarray = jsonArray.getJSONObject(i).getJSONObject("mblog").getJSONArray("pics");
                            if(picsarray!=null){
                                for (int n=0;n<picsarray.size();n++){
                                    System.out.println(picsarray.getJSONObject(n).getJSONObject("large").get("url"));
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


        }*/
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void redisTest() {
        //存值
        stringRedisTemplate.opsForValue().set("myname","zt");
        //取值
        System.out.println(stringRedisTemplate.opsForValue().get("myname"));
        //遍历所有键值对
        Set<String> keys = stringRedisTemplate.keys("*");
        HashMap<String, String> map = new HashMap<>();
        for (String key : keys) {
            //String类型的键值获取
            String value = stringRedisTemplate.opsForValue().get(key);
            map.put(key, value);
        }
        System.out.println(map);
    }

    @Test
    void threadTest() {
        ThreadPoolExecutor poolExecutor =
                new ThreadPoolExecutor(10, 10, 1000L,
                        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            final int b = i;
            poolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程开始");
                try {
                    for (int c = 0; c < 20; c++) {
                        Thread.sleep(500);
                        stringRedisTemplate.opsForValue().set(b + "_key", String.valueOf(c));
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程结束");
            });
        }
    }


}
