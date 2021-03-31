package com.example.demo3.test.service;

import com.example.demo3.test.dao.PicInstance;
import com.github.pagehelper.PageInfo;

public interface IPicInstanceService {

    public PageInfo<PicInstance> getAllPic(int pagenum,int pagesize);

    public int insert(PicInstance record);
}
