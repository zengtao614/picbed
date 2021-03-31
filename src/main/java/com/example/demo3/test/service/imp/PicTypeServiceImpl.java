package com.example.demo3.test.service.imp;

import com.example.demo3.test.dao.PicType;
import com.example.demo3.test.dao.PicTypeMapper;
import com.example.demo3.test.service.IPicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PicTypeServiceImpl implements IPicTypeService {

    @Resource
    private PicTypeMapper picTypeMapper;

    @Override
    public List<PicType> getAllType() {
        return picTypeMapper.selectAllType();
    }

    @Override
    public PicType getPicTypeById(String id) {
        return picTypeMapper.selectByPrimaryKey(id);
    }
}
