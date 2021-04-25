package com.example.demo3.test.web.admin;

import com.example.demo3.test.dao.CrawlerLog;
import com.example.demo3.test.dao.PicSource;
import com.example.demo3.test.dao.PicType;
import com.example.demo3.test.service.IPicInstanceService;
import com.example.demo3.test.service.IPicSourceService;
import com.example.demo3.test.service.IPicTypeService;
import com.example.demo3.test.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-12
 * @Content: 爬虫启动控制器，需要管理员权限才能请求进入
 */
@Controller
@RequestMapping("/admin")
public class CrawlerController {

    @Autowired
    private IPicTypeService picTypeService;
    @Autowired
    private IPicSourceService picSourceService;
    @Autowired
    private IPicInstanceService picInstanceService;

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
    public void startHbCrawler(@RequestParam(name = "spanname") String spanname, @RequestParam(name = "typecode") String typecode, @RequestParam(name = "sourcecode") String sourcecode){
        picInstanceService.grabHbByspan(spanname,typecode,sourcecode);
    }

    @RequestMapping("/starthbcrawlerbyid")
    @ResponseBody
    public void starthbcrawlerbyid(@RequestParam(name = "boardid") String boardid,@RequestParam(name = "typecode") String typecode,@RequestParam(name = "sourcecode") String sourcecode){
        picInstanceService.grabHbByid(boardid,typecode,sourcecode);
    }

    @RequestMapping("/startwbcrawlerbyid")
    public String startwbcrawlerbyid(Model model,@RequestParam(name = "containerid") String containerid,@RequestParam(name = "typecode") String typecode,@RequestParam(name = "sourcecode") String sourcecode){
        int i = picInstanceService.grabWbByidTest(containerid, typecode, sourcecode);
        model.addAttribute("taskcount",i);
        model.addAttribute("containerid",containerid);
        //return "crawler_schedule.html";
        return "redirect:/admin/getAllCrawler";
    }

    @RequestMapping("/getAllCrawler")
    public String getAllCrawler(Model model){
        List<CrawlerLog> crawlerList = picInstanceService.getAllCrawler();
        model.addAttribute("crawlerList",crawlerList);
        return "crawlerSchedule.html";
    }

    @RequestMapping("/getprogress")
    @ResponseBody
    public Map getprogress(Model model, @RequestParam(name = "containerid") String containerid){
        Map result = picInstanceService.getprogress(containerid);
        return result;
    }

    @RequestMapping("/shutdownthread")
    @ResponseBody
    public Map  shutdownthread(){
        Map map = picInstanceService.shutdownThreadPool();
        return map;
    }


    @RequestMapping("/picmanage")
    public String  picmanage(Model model){
        Map picdata = picInstanceService.getPicdata();
        model.addAttribute("picdata",picdata);
        return "picmanage.html";
    }


    @RequestMapping("/sudodownloadpic")
    @ResponseBody
    public String  sudodownloadpic(){
        picInstanceService.sudodownloadpic();
        return "下载启动";
    }

}
