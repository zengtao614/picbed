package com.example.demo3.test.crawler;

import com.example.demo3.test.service.IPicInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Hbcrawler {

    private static String suggestsRegex = "app.page\\[\"suggests\"\\] = \\{.*\\}";
    private static String urlRegex = "\"url\":\".*?\"";
    private static String boardsRegex = "app.page\\[\"boards\"\\] = \\[.*\\]";
    private static String boardidRegex = "\"board_id\":.*?,";
    private static final Pattern BOARDID_PATTERN = Pattern.compile("[\\d]+");

    public static String folder_name;//存储路径

    public static String nginxsite;//静态服务器端口
    private String url;//
    private String typecode;
    private String sourcecode;
    private IPicInstanceService picInstanceService;

    public Hbcrawler(){

    }

    @Value("${crawler.foldername}")
    private void setFoldername(String foldername){
        folder_name = foldername;
    }
    @Value("${crawler.nginxsite}")
    private void setNginxsite(String nginxSite){
        nginxsite = nginxSite;
    }

    public Hbcrawler(String url) {
        this.url = url;
    }

    public Hbcrawler(String spanname,String typecode,String sourcecode,IPicInstanceService picInstanceService) {
        this.url = "https://huaban.com/boards/favorite/" + spanname;
        //this.url = "https://huaban.com/boards/" + spanname;
        this.picInstanceService = picInstanceService;
        this.typecode = typecode;
        this.sourcecode = sourcecode;
    }

    public static void main(String[] args) throws Exception {
        String url = "https://huaban.com/boards/favorite/anime/";
        new Hbcrawler(url).startCrawle();

        //单独爬取某一画板
        //new Crawler("https://huaban.com/boards/67259355/","D:\\HbCrawler\\52334297\\").start();
    }

    /**
     * 获取每个画板的url并启动爬虫
     *
     * @throws Exception
     */
    public void startCrawle() throws Exception {
        StringBuffer html = getHtml(url);
        Matcher matcherSu = Pattern.compile(boardsRegex).matcher(html);
        while (matcherSu.find()) {
            Matcher matcherUrl = Pattern.compile(boardidRegex).matcher(matcherSu.group());
            while (matcherUrl.find()) {
                String urlStr = matcherUrl.group();
                if (urlStr.contains("board_id")) {
                    Matcher matcherId = BOARDID_PATTERN.matcher(urlStr);
                    String id = "";
                    while (matcherId.find()) {
                        id = matcherId.group();
                    }
                    String url = "https://huaban.com/boards/" + id;
                    String folderName = folder_name + id;
                    new Crawler(url, folderName + "\\",id,typecode,sourcecode,picInstanceService).start();
                }
            }
        }
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
        if (files!=null){
            for (File file1 : files) {
                file1.delete();
            }
        }
        file.delete();
    }
}
