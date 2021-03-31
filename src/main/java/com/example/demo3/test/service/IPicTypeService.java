package com.example.demo3.test.service;

import com.example.demo3.test.dao.PicType;

import java.util.List;

public interface IPicTypeService {

    public List<PicType> getAllType();

    public PicType getPicTypeById(String id);
}
