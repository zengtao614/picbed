package com.example.demo3.test.service.imp;

import com.example.demo3.test.dao.PicSource;
import com.example.demo3.test.dao.PicSourceMapper;
import com.example.demo3.test.service.IPicSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicSourceServiceImpl implements IPicSourceService {

    @Autowired
    private PicSourceMapper picSourceMapper;

    @Override
    public List<PicSource> getAllSource() {
        return picSourceMapper.getAllSource();
    }
}
