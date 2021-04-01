package com.example.demo3.test.service;

import com.example.demo3.test.dao.PicType;

import java.util.List;

/**
 * @author zt
 */
public interface IPicTypeService {

    /**
     * 获取所有图片类型数据
     * @return
     */
    public List<PicType> getAllType();

    /**
     * 根据图片类型代码获取图片类型实例
     * @param id 图片类型代码
     * @return
     */
    public PicType getPicTypeById(String id);
}
