package com.example.demo3.test.web;



import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.dao.PicSource;
import com.example.demo3.test.dao.PicType;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.service.IPicSourceService;
import com.example.demo3.test.service.IPicTypeService;
import com.example.demo3.test.util.SpiderUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private IPicTypeService picTypeService;
    @Autowired
    private IPicSourceService picSourceService;
    @Autowired
    private IPicInstanceService picInstanceService;



    @RequestMapping("/")
    public String pictype(Model model, PageInfo pageInfo){
        int pageNum  = (pageInfo.getPageNum() == 0)? 1 : pageInfo.getPageNum();
        int pageSize  = (pageInfo.getPageSize() == 0)? 12 : pageInfo.getPageSize();
        //查询所有图片类型
        List<PicType> allType = picTypeService.getAllType();
        //查询所有图片来源
        List<PicSource> allSource = picSourceService.getAllSource();
        //分页查询所有图片
        PageInfo<PicInstance> allPic = picInstanceService.getAllPic(pageNum, pageSize);
        model.addAttribute("allPic",allPic);
        model.addAttribute("allType",allType);
        model.addAttribute("allSource",allSource);
        model.addAttribute("nginxsite", SpiderUtil.nginxsite);
        return "index.html";
    }

    @RequestMapping("/getpictype")
    public String getpictype(Model model){
        PicType picType = picTypeService.getPicTypeById("1");
        model.addAttribute("picType",picType);
        return "index.html";
    }

    @RequestMapping("/startcrawler")
    public String startcrawler(Model model){
        //查询所有已配置爬虫

        //查询所有图片类型
        List<PicType> allType = picTypeService.getAllType();
        //查询所有图源
        List<PicSource> allSource = picSourceService.getAllSource();
        model.addAttribute("allType",allType);
        model.addAttribute("allSource",allSource);
        return "crawlerManage.html";
    }


    @RequestMapping("/starthbcrawler")
    @ResponseBody
    public void startHbCrawler(@RequestParam(name = "spanname") String spanname,@RequestParam(name = "typecode") String typecode,@RequestParam(name = "sourcecode") String sourcecode){
        picInstanceService.grabHbByspan(spanname,typecode,sourcecode);
    }

    @RequestMapping("/starthbcrawlerbyid")
    @ResponseBody
    public void starthbcrawlerbyid(@RequestParam(name = "boardid") String boardid,@RequestParam(name = "typecode") String typecode,@RequestParam(name = "sourcecode") String sourcecode){
        picInstanceService.grabHbByid(boardid,typecode,sourcecode);
    }

    @RequestMapping("/startwbcrawlerbyid")
    @ResponseBody
    public void startwbcrawlerbyid(@RequestParam(name = "containerid") String containerid,@RequestParam(name = "typecode") String typecode,@RequestParam(name = "sourcecode") String sourcecode){
        picInstanceService.grabWbByid(containerid,typecode,sourcecode);
    }

}
