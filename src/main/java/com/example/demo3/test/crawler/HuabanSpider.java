package com.example.demo3.test.crawler;

import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.SpiderUtil;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-1
 * @Content:
 */
public class HuabanSpider extends Spider {

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

    private String boardid;

    public HuabanSpider(String boardid, String typecode, String sourcecode, IPicInstanceService picInstanceService){
        this.boardid = boardid;
        this.typecode = typecode;
        this.sourcecode = sourcecode;
        this.picInstanceService = picInstanceService;
        this.url = HUABAN_BOARDSITE + boardid;
    }


    @Override
    public void run() {
        try {
            File file = new File(SpiderUtil.folder_name + boardid + "\\");
            if (file.exists()) {
                SpiderUtil.delete(file);
            }
            file.mkdirs();
            String thisUrl = url;
            while (true) {
                StringBuffer html = SpiderUtil.getHtml(thisUrl);
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
                    String srcUrl = "http:" + srcStr.substring(1, srcStr.length() - 1).replace("fw236", "fw658");
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
            String picname = src.substring(src.lastIndexOf("/") + 1) + ".jpg";
            try {
                if (picInstanceService != null) {
                    //数据库存储操作
                    PicInstance picInstance = new PicInstance();
                    picInstance.setId(UUID.randomUUID().toString());
                    picInstance.setPicName(picname);
                    picInstance.setPicSource(Integer.valueOf(sourcecode));
                    picInstance.setPicTypecode(Integer.valueOf(typecode));
                    picInstance.setPicUrl(boardid + "/" + picInstance.getPicName());
                    picInstanceService.insert(picInstance);
                    //下载图片到本地
                    SpiderUtil.downloadpic(src,SpiderUtil.folder_name + boardid + "\\",picname);
                    System.out.println(picname + "保存成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(picname + "保存失败");
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
}
