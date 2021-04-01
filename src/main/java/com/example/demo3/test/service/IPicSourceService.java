package com.example.demo3.test.service;

import com.example.demo3.test.dao.PicSource;

import java.util.List;

/**
 * @author zt
 */
public interface IPicSourceService {

    /**
     * 获取所有图片源数据
     * @return
     */
    public List<PicSource> getAllSource();
}
