package com.example.demo3.test.service;

import com.example.demo3.test.dao.BlogUser;
import com.example.demo3.test.dao.CrawlerLog;
import com.example.demo3.test.dao.PicInstance;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zt
 */
public interface IPicInstanceService {

    /**
     * 查询所有图片
     * @param pagenum 分页参数，当前页数
     * @param pagesize 分页参数，每页数量
     * @return 返回pageinfo对象，包含查询结果集和分页数据
     */
    public PageInfo<PicInstance> getAllPic(int pagenum, int pagesize, Map params);

    /**
     * 新增方法，新增一条图片数据到数据库
     * @param record 新增图片数据实例对象
     * @return 返回影响行数，一般返回1表示新增成功
     */
    public int insert(PicInstance record);

    /**
     * 抓取花瓣网图片，通过画板id抓取单个画板所有图片
     * @param boardid 画板id
     * @param typecode 图片类型代码
     * @param sourcecode 图片来源代码
     */
    void grabHbByid(String boardid, String typecode, String sourcecode);

    /**
     * 抓取花瓣网图片，通过标签名抓取标签下所有画板
     * @param spanname 标签名
     * @param typecode 图片类型代码
     * @param sourcecode 图片来源代码
     */
    void grabHbByspan(String spanname, String typecode, String sourcecode);

    /**
     * 抓取微博用户配图，通过用户containerid来标识唯一用户
     * @param containerid 标识唯一用户，需要特殊手段获取
     * @param typecode 图片类型代码
     * @param sourcecode 图片来源代码
     */
    void grabWbByid(String containerid, String typecode, String sourcecode);

    /**
     *
     * @param containerid
     * @param typecode
     * @param sourcecode
     * @return
     */
    int grabWbByidTest(String containerid, String typecode, String sourcecode);

    /**
     * 随机查询20条数据
     * @return 返回实例列表
     */
    List<PicInstance> getRandomPic();

    Map shutdownThreadPool();

    /**
     * 获取存活的爬虫实例数据
     * @return
     */
    List<CrawlerLog> getAllCrawler();

    /**
     * 获取未下载的图片实例
     * @return
     */
    List<PicInstance> getNeeddownpic();

    /**
     * 获取对应博主下未下载的图片实例
     * @return
     */
    List<PicInstance> getNeeddownpicForbloguser(String containerid);

    /**
     * 获取未下载的图片实例（测试方法，每次只获取20实例）
     * @return
     */
    List<PicInstance> getNeeddownpicTest();

    /**
     * 修改方法
     * @param p
     * @return
     */
    int updatePicinstance(PicInstance p);

    /**
     * 获取数据库图片基本数据
     * @return
     */
    Map getPicdata();

    /**
     * 立即下载未下载的图片
     */
    Map sudodownloadpic();

    /**
     * 爬虫完成后将爬虫状态设为0（死亡）
     * @param containerid
     */
    void setDeadSpider(String containerid);

    /**
     * 根据爬虫参数获取爬虫进度
     * @param containerid
     * @return
     */
    Map getprogress(String containerid);

    /**
     * 获取数据库所有已爬取的博主数据以及博主对应的图片数据
     * @return
     */
    List<BlogUser> getAllBlogUser();

    /**
     * 获取数据库图片基本数据从redis中查询
     * @return
     */
    Map getPicdataInredis();

    /**
     * 根据爬虫参数id删除所有此id对应的图片数据以及博主图片(本地图片不删，只删除数据库数据)
     * @param containerid
     * @return
     */
    void deletepicForbloguser(String containerid);

    /**
     * 下载某个博主下所有图片
     * @param containerid
     */
    void downloadpicForbloguser(String containerid);

    /**
     * 获取某个博主下所有图片数据（全部数量，已下载未下载数量）
     * @param containerid
     * @return
     */
    Map<String, Long> getPicdataForbloguser(String containerid);

    /**
     * 获取某个博主的数据库图片基本数据从redis中查询
     * @param containerid
     * @return
     */
    Map getBloguserpicdataInredis(String containerid);
}
