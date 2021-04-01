package com.example.demo3.test.service.imp;

import com.example.demo3.test.crawler.HuabanSpider;
import com.example.demo3.test.crawler.WeiboSpider;
import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.dao.PicInstanceMapper;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.util.SpiderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zt
 */
@Service
public class PicInstanceServiceImpl implements IPicInstanceService {

    @Autowired
    private PicInstanceMapper picInstanceMapper;
    @Override
    public PageInfo<PicInstance> getAllPic(int pagenum, int pagesize) {
        PageHelper.startPage(pagenum,pagesize);
        List<PicInstance> picInstanceList = picInstanceMapper.getAllPic();
        PageInfo<PicInstance> pageInfo = new PageInfo<>(picInstanceList);
        return pageInfo;
    }

    @Override
    public int insert(PicInstance record) {
        return picInstanceMapper.insert(record);
    }

    @Override
    public void grabHbByid(String boardid, String typecode, String sourcecode) {
        new HuabanSpider(boardid,typecode,sourcecode,this).start();
    }

    @Override
    public void grabHbByspan(String spanname, String typecode, String sourcecode) {
        try {
            StringBuffer html = SpiderUtil.getHtml(HuabanSpider.HUABAN_SPANSITE+spanname);
            Matcher matcherSu = Pattern.compile(HuabanSpider.boardsRegex).matcher(html);
            while (matcherSu.find()) {
                Matcher matcherUrl = Pattern.compile(HuabanSpider.boardidRegex).matcher(matcherSu.group());
                while (matcherUrl.find()) {
                    String urlStr = matcherUrl.group();
                    if (urlStr.contains("board_id")) {
                        Matcher matcherId = HuabanSpider.BOARDID_PATTERN.matcher(urlStr);
                        String id = "";
                        while (matcherId.find()) {
                            id = matcherId.group();
                        }
                        new HuabanSpider(id,typecode,sourcecode,this).start();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void grabWbByid(String containerid, String typecode, String sourcecode) {
        new WeiboSpider(containerid,typecode,sourcecode,this).start();
    }
}
