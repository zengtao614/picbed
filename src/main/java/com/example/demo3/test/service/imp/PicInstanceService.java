package com.example.demo3.test.service.imp;

import com.example.demo3.test.dao.PicInstance;
import com.example.demo3.test.dao.PicInstanceMapper;
import com.example.demo3.test.service.IPicInstanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicInstanceService implements IPicInstanceService {

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
}
