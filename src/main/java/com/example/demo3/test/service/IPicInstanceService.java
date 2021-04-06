package com.example.demo3.test.service;

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
     * 随机查询20条数据
     * @return 返回实例列表
     */
    List<PicInstance> getRandomPic();
}
