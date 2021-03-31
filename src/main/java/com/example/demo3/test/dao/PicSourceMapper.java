package com.example.demo3.test.dao;

import com.example.demo3.test.dao.PicSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PicSourceMapper {
    int deleteByPrimaryKey(String id);

    int insert(PicSource record);

    int insertSelective(PicSource record);

    PicSource selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PicSource record);

    int updateByPrimaryKey(PicSource record);

    @Select("select id,source_name sourceName,source_code sourceCode,source_url sourceUrl from PICSOURCE")
    List<PicSource> getAllSource();
}