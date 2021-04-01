package com.example.demo3.test.service;

import com.example.demo3.test.dao.PicInstance;
import com.github.pagehelper.PageInfo;

public interface IPicInstanceService {

    /**
     * 查询所有图片
     * @param pagenum 分页参数，当前页数
     * @param pagesize 分页参数，每页数量
     * @return 返回pageinfo对象，包含查询结果集和分页数据
     */
    public PageInfo<PicInstance> getAllPic(int pagenum,int pagesize);

    /**
     * 新增方法，新增一条图片数据到数据库
     * @param record 新增图片数据实例对象
     * @return 返回影响行数，一般返回1表示新增成功
     */
    public int insert(PicInstance record);
}
