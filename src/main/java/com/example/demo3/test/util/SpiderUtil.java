package com.example.demo3.test.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-1
 * @Content: 爬虫工具类
 */
@Component
public class SpiderUtil {

    /**
     * 存储路径
     */
    public static String folder_name;
    /**
     * 静态服务器地址
     */
    public static String nginxsite;

    @Value("${crawler.foldername}")
    private void setFoldername(String foldername) {
        folder_name = foldername;
    }

    @Value("${crawler.nginxsite}")
    private void setNginxsite(String nginxSite) {
        nginxsite = nginxSite;
    }

    /**
     * 传入url返回html
     *
     * @param url 传入的url参数
     * @return 返回html
     * @throws Exception
     */
    public static StringBuffer getHtml(String url) throws Exception {
        URL url1 = new URL(url);
        URLConnection connection = url1.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader bw = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        while (bw.ready()) {
            sb.append(bw.readLine()).append("\n");
        }
        bw.close();
        is.close();
        return sb;

    }

    /**
     * 删除目录下所有文件包括该目录，如果该目录下有不为空的目录则会删除失败
     *
     * @param file 传入的File对象
     */
    public static void delete(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                file1.delete();
            }
        }
        file.delete();
    }

    /**
     * 下载图片方法，使用io流方式
     * @param src 图片资源地址
     * @param foldername 图片下载位置
     * @param picname 图片名称
     * @throws Exception
     */
    public static void downloadpic(String src, String foldername, String picname) throws Exception {
        //文件下载操作
        URL url = new URL(src);
        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream(foldername + picname);
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = is.read(buf)) != -1) {
            fos.write(buf, 0, length);
        }
        fos.close();
        is.close();
    }

    public static String getResponse(String url) throws Exception{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == 200){
            return EntityUtils.toString(responseEntity);
        }
        throw new Exception("网络请求出错");

    }


}
