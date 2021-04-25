package com.example.demo3.test.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.test.crawler.PicCrawler;
import com.example.demo3.test.dao.*;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.RedisUtil;
import com.example.demo3.test.util.SpiderUtil;
import com.example.demo3.test.util.SpringSecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zt
 */
@Service
public class PicInstanceServiceImpl implements IPicInstanceService {

    @Autowired
    private PicInstanceMapper picInstanceMapper;
    @Autowired
    private CrawlerLogMapper crawlerLogMapper;
    @Autowired
    private PicCrawler picCrawler;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public PageInfo<PicInstance> getAllPic(int pagenum, int pagesize, Map params) {
        PageHelper.startPage(pagenum,pagesize);
        List<PicInstance> picInstanceList = picInstanceMapper.getAllPic(params);
        PageInfo<PicInstance> pageInfo = new PageInfo<>(picInstanceList);
        return pageInfo;
    }

    @Override
    public int insert(PicInstance record) {
        return picInstanceMapper.insert(record);
    }

    @Override
    public void grabHbByid(String boardid, String typecode, String sourcecode) {
        picCrawler.hbspiderRun(boardid,typecode,sourcecode);
    }

    @Override
    public void grabHbByspan(String spanname, String typecode, String sourcecode) {
        try {
            StringBuffer html = SpiderUtil.getHtml(SpiderUtil.HUABAN_SPANSITE+spanname);
            Matcher matcherSu = Pattern.compile(SpiderUtil.boardsRegex).matcher(html);
            while (matcherSu.find()) {
                LinkedHashSet<String> idSet = new LinkedHashSet<>();
                Matcher matcherUrl = Pattern.compile(SpiderUtil.boardidRegex).matcher(matcherSu.group());
                while (matcherUrl.find()) {
                    String urlStr = matcherUrl.group();
                    String id = "";
                    if (urlStr.contains("board_id")) {
                        Matcher matcherId = SpiderUtil.BOARDID_PATTERN.matcher(urlStr);
                        while (matcherId.find()) {
                            id = matcherId.group();
                            idSet.add(id);
                        }
                    }
                }
                for (String boardid:idSet){
                    picCrawler.hbspiderRun(boardid,typecode,sourcecode);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void grabWbByid(String containerid, String typecode, String sourcecode) {
        try {
            SpiderUtil.createFolder(SpiderUtil.folder_name + containerid + "/");
            String url = SpiderUtil.weibo_baseurl + containerid;
            JSONObject pagejson = JSON.parseObject(SpiderUtil.getResponse(url));
            if("1".equals(String.valueOf(pagejson.get("ok")))){
                int total = (Integer)(pagejson.getJSONObject("data").getJSONObject("cardlistInfo").get("total"));
                int page = (total/10) + 1;
                for (int i=0;i<=page;i++){
                    picCrawler.wbspiderRun(url,containerid,typecode,sourcecode);
                }
            }else{
                System.out.println("此用户下无微博！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int grabWbByidTest(String containerid, String typecode, String sourcecode) {
        try {
            //获取当前登录用户
            User user = SpringSecurityUtil.currentUser();
            SpiderUtil.createFolder(SpiderUtil.folder_name + containerid + "/");
            String url = SpiderUtil.weibo_baseurl + containerid;
            JSONObject pagejson = JSON.parseObject(SpiderUtil.getResponse(url));
            if("1".equals(String.valueOf(pagejson.get("ok")))){
                int total = (Integer)(pagejson.getJSONObject("data").getJSONObject("cardlistInfo").get("total"));
                int page = (total/10) + 1;
                /**
                 * 创建爬虫记录数据
                 */
                CrawlerLog crawlerLog = new CrawlerLog();
                crawlerLog.setCraCreater(String.valueOf(user.getId()));
                Date date = new Date();
                crawlerLog.setCraDate(date);
                crawlerLog.setCraIsalive(1);
                crawlerLog.setCraSource(Integer.valueOf(sourcecode));
                crawlerLog.setCraType(Integer.valueOf(typecode));
                crawlerLog.setCraParam(containerid);
                crawlerLog.setCraThreadnum(page);
                crawlerLog.setCraInfo(user.getUsername() + "于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
                        + "创建了一个类型为" + typecode + "来源为" + sourcecode + "参数为" + containerid + "的爬虫");
                crawlerLogMapper.insert(crawlerLog);
                /**
                 * ============创建爬虫记录数据==============
                 */
                redisUtil.setString(containerid + ":allpage",String.valueOf(0));
                redisUtil.setString(containerid + ":countpage",String.valueOf(page));
                for (int i=1;i<=page;i++){
                    String pageurl = url + "&page=" + i;
                    picCrawler.wbspiderRun(pageurl,containerid,typecode,sourcecode,i);
                }
                return page;
            }else{
                System.out.println("此用户下无微博！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<PicInstance> getRandomPic() {
        return picInstanceMapper.getRandomPic();
    }

    @Override
    public Map shutdownThreadPool() {
        return picCrawler.shutdownThreadPool();
    }

    @Override
    public List<CrawlerLog> getAllCrawler() {
        return crawlerLogMapper.getAllCrawler();
    }

    @Override
    public List<PicInstance> getNeeddownpic() {
        return picInstanceMapper.getNeeddownpic();
    }

    @Override
    public List<PicInstance> getNeeddownpicTest() {
        return picInstanceMapper.getNeeddownpicTest();
    }

    @Override
    public int updatePicinstance(PicInstance p) {
        return picInstanceMapper.updateByPrimaryKey(p);
    }

    @Override
    public Map getPicdata() {
        return picInstanceMapper.getPicdata();
    }

    @Override
    public void sudodownloadpic() {
        System.out.println("手动下载图片任务开始，当前时间为:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<PicInstance> picList = getNeeddownpic();
        int successDown = 0;
        int failDown = 0;
        for (PicInstance p:picList){
            try {
                SpiderUtil.downloadpic(p.getPicOriurl(),SpiderUtil.folder_name + p.getPicUrl());
                p.setPicHasdown(1);
                updatePicinstance(p);
                successDown += 1;
                System.out.println(p.getPicName() + "下载成功");
            }catch (Exception e){
                e.printStackTrace();
                failDown += 1;
                System.out.println(p.getPicName() + "下载失败");
            }
        }
        System.out.println("定时下载图片任务结束,当前时间为:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+";下载成功"+successDown+"张，下载失败"+failDown+"张。");
    }

    @Override
    public void setDeadSpider(String containerid) {
        crawlerLogMapper.setDeadSpider(containerid);
    }

    @Override
    public Map getprogress(String containerid) {
        Map spiderProgress = redisUtil.getPattern("*" + containerid + ":*");
        /**
         * 判断进度数是否等于总数，如果等于则判断此爬虫爬取完毕，删掉redis中对应的所有数据。
         */
        String allpage = (String) spiderProgress.get(containerid+":allpage");
        String countpage = (String) spiderProgress.get(containerid+":countpage");
        if (allpage.equals(countpage)){
            redisUtil.removeStringPattern(containerid+":*");
        }
        return spiderProgress;
    }
}
