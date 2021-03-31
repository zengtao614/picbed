package com.example.demo3.test.crawler;

import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.dao.PicInstanceMapper;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.service.imp.PicInstanceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler extends Thread {

    private String url;
    private String folderName;
    private String boardid;
    private String typecode;
    private String sourcecode;
    private IPicInstanceService picInstanceService;



    private static String get_img_regex = "<img src=\"//hbimg.*?/>";
    private static String get_src_regex = "\"//hbimg.huabanimg.com/.*?\"";
    private static String get_dataId_regex = "pin_id:'[\\d]+'";

    public Crawler(String url, String folderName,String boardid,String typecode,String sourcecode,IPicInstanceService picInstanceService) {
        this.url = url;
        this.folderName = folderName;
        this.boardid = boardid;
        this.typecode = typecode;
        this.sourcecode = sourcecode;
        this.picInstanceService = picInstanceService;
    }

    public Crawler(String boardid,String typecode,String sourcecode,IPicInstanceService picInstanceService) {
        this.url = "https://huaban.com/boards/" + boardid;
        this.folderName = Hbcrawler.folder_name + boardid + "\\";
        this.boardid = boardid;
        this.typecode = typecode;
        this.sourcecode = sourcecode;
        this.picInstanceService = picInstanceService;
    }

    @Override
    public void run() {
        try {
            File file = new File(folderName);
            if (file.exists()) {
                Hbcrawler.delete(file);
            }
            file.mkdirs();
            String thisUrl = url;
            while (true) {
                StringBuffer html = Hbcrawler.getHtml(thisUrl);
                LinkedHashSet<String> srcSet = getSrc(html);
                if (srcSet.size() == 0) {
                    System.out.println(url + "页面已爬取完成");
                    break;
                }
                saveImg(srcSet);
                thisUrl = getNextUrl(html);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取html中src标签内容
     *
     * @param html 传入的html文本
     * @return 返回src列表
     */
    public LinkedHashSet<String> getSrc(StringBuffer html) {
        LinkedHashSet<String> srcSet = new LinkedHashSet<>();
        Matcher matcherImg = Pattern.compile(get_img_regex).matcher(html);
        while (matcherImg.find()) {
            Matcher matcherSrc = Pattern.compile(get_src_regex).matcher(matcherImg.group());
            while (matcherSrc.find()) {
                String srcStr = matcherSrc.group();
                if (srcStr.contains("fw236")) {
                    String srcUrl = "http:"+srcStr.substring(1, srcStr.length() - 1).replace("fw236", "fw658");
                    srcSet.add(srcUrl);
                }
            }
        }
        return srcSet;
    }

    /**
     * 根据src地址下载该图片
     *
     * @param srcSet 传入的src列表
     */
    public void saveImg(LinkedHashSet<String> srcSet) {
        for (String src : srcSet) {
            try {

                if (picInstanceService!=null){
                    //数据库存储操作
                    PicInstance picInstance = new PicInstance();
                    picInstance.setId(UUID.randomUUID().toString());
                    picInstance.setPicName(src.substring(src.lastIndexOf("/") + 1) + ".jpg");
                    picInstance.setPicSource(Integer.valueOf(sourcecode));
                    picInstance.setPicTypecode(Integer.valueOf(typecode));
                    picInstance.setPicUrl(boardid+"/"+picInstance.getPicName());//下载到本地(Hbcrawler.nginxsite)
//                    picInstance.setPicUrl(src);//只存取资源路径
                    picInstanceService.insert(picInstance);
                }
                //文件下载操作
                URL url = new URL(src);
                InputStream is = url.openStream();
                FileOutputStream fos = new FileOutputStream(folderName + src.substring(src.lastIndexOf("/") + 1) + ".jpg");
                byte buf[] = new byte[1024];
                int length = 0;
                while ((length = is.read(buf)) != -1) {
                    fos.write(buf, 0, length);
                }
                fos.close();
                is.close();
                System.out.println(src.substring(src.lastIndexOf("/") + 1) + ".jpg" + "下载成功");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(src.substring(src.lastIndexOf("/") + 1) + ".jpg" + "下载失败");
            }
        }
    }

    /**
     * 实现页面的ajax加载，获取下次请求的url
     *
     * @param html
     * @return
     */
    public String getNextUrl(StringBuffer html) {
        String last_id = null;
        try {
            Matcher matcher = Pattern.compile(get_dataId_regex).matcher(html);
            while (matcher.find()) {
                last_id = matcher.group();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lastId = last_id.substring(last_id.indexOf("'") + 1, last_id.lastIndexOf("'"));
        String nextUrl = url + "/?max=" + lastId;
        return nextUrl;
    }
}