package com.example.demo3.test.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.test.crawler.PicCrawler;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.dao.PicInstanceMapper;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.SpiderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
    private PicCrawler picCrawler;

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
            SpiderUtil.createFolder(SpiderUtil.folder_name + containerid + "\\");
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
    public List<PicInstance> getRandomPic() {
        return picInstanceMapper.getRandomPic();
    }
}
