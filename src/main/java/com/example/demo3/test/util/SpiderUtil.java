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
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-1
 * @Content: 爬虫工具类
 */
@Component
public class SpiderUtil {


    /**
     * =========================花瓣网爬取相关正则================
     */
    public static String suggestsRegex = "app.page\\[\"suggests\"\\] = \\{.*\\}";
    public static String urlRegex = "\"url\":\".*?\"";
    public static String boardsRegex = "app.page\\[\"boards\"\\] = \\[.*\\]";
    public static String boardidRegex = "\"board_id\":.*?,";
    public static final Pattern BOARDID_PATTERN = Pattern.compile("[\\d]+");

    private static String get_img_regex = "<img src=\"//hbimg.*?/>";
    private static String get_src_regex = "\"//hbimg.huabanimg.com/.*?\"";
    private static String get_dataId_regex = "pin_id:'[\\d]+'";
    public static final String HUABAN_BOARDSITE = "https://huaban.com/boards/";
    public static final String HUABAN_SPANSITE = "https://huaban.com/boards/favorite/";
    /**
     * =========================花瓣网爬取相关正则================
     */


    /**
     * 微博爬虫的基础链接
     */
    public static String weibo_baseurl = "https://m.weibo.cn/api/container/getIndex?containerid=";

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
     * 根据路径创建文件夹，如果存在该文件夹，会先删除此文件夹及文件夹下所有子文件
     * @param path
     */
    public static void createFolder(String path){
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    file1.delete();
                }
            }
            file.delete();
        }
        file.mkdirs();
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
        FileOutputStream fos = new FileOutputStream(foldername + "/" + picname);
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = is.read(buf)) != -1) {
            fos.write(buf, 0, length);
        }
        fos.close();
        is.close();
    }

    /**
     * 使用第三方请求库httpclient来请求url，比原生更加好用
     * @param url
     * @return
     */
    public static String getResponse(String url){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(responseEntity);
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
        return null;
    }

    /**
     * ============================花瓣网相关工具方法===========================
     */

    /**
     * 获取html中src标签内容
     *
     * @param html 传入的html文本
     * @return 返回src列表
     */
    public static LinkedHashSet<String> getSrc(StringBuffer html) {
        LinkedHashSet<String> srcSet = new LinkedHashSet<>();
        Matcher matcherImg = Pattern.compile(get_img_regex).matcher(html);
        while (matcherImg.find()) {
            Matcher matcherSrc = Pattern.compile(get_src_regex).matcher(matcherImg.group());
            while (matcherSrc.find()) {
                String srcStr = matcherSrc.group();
                if (srcStr.contains("fw236")) {
                    String srcUrl = "http:" + srcStr.substring(1, srcStr.length() - 1).replace("fw236", "fw658");
                    srcSet.add(srcUrl);
                }
            }
        }
        return srcSet;
    }

    /**
     * 实现页面的ajax加载，获取下次请求的url
     *
     * @param html
     * @return
     */
    public static String getNextUrl(StringBuffer html,String url) {
        String lastIdStr = null;
        try {
            Matcher matcher = Pattern.compile(get_dataId_regex).matcher(html);
            while (matcher.find()) {
                lastIdStr = matcher.group();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lastId = lastIdStr.substring(lastIdStr.indexOf("'") + 1, lastIdStr.lastIndexOf("'"));
        String nextUrl = url + "/?max=" + lastId;
        return nextUrl;
    }

    /**
     * ============================花瓣网相关工具方法===========================
     */

}
