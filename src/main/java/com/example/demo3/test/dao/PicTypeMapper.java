package com.example.demo3.test.dao;

import com.example.demo3.test.dao.PicType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PicTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(PicType record);

    int insertSelective(PicType record);

    PicType selectByPrimaryKey(String id);

    @Select("select id,type_name typeName,type_code typeCode from PICTYPE")
    List<PicType> selectAllType();

    int updateByPrimaryKeySelective(PicType record);

    int updateByPrimaryKey(PicType record);
}