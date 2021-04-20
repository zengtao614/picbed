package com.example.demo3.test.dao;

import com.example.demo3.test.dao.CrawlerLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrawlerLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrawlerLog record);

    int insertSelective(CrawlerLog record);

    CrawlerLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrawlerLog record);

    int updateByPrimaryKey(CrawlerLog record);

    List<CrawlerLog> getAllCrawler();
}