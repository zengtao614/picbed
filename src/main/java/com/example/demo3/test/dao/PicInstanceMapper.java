package com.example.demo3.test.dao;

import com.example.demo3.test.dao.PicInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@Mapper
public interface PicInstanceMapper {

    int deleteByPrimaryKey(String id);

    int insert(PicInstance record);

    int insertSelective(PicInstance record);

    PicInstance selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PicInstance record);

    int updateByPrimaryKey(PicInstance record);

    List<PicInstance> getAllPic(Map params);

    @Select("select * from PICINSTANCE")
    List<PicInstance> getAllPicBypage();
}