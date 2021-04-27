package com.example.demo3.test.dao;

import com.example.demo3.test.dao.BlogUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogUser record);

    int insertSelective(BlogUser record);

    BlogUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BlogUser record);

    int updateByPrimaryKey(BlogUser record);

    /**
     * 根据爬虫资源id获取博主对象
     * @param containerid
     * @return
     */
    BlogUser selectByContainerid(String containerid);

    /**
     * 获取数据库所有已爬取的博主数据
     * @return
     */
    List<BlogUser> getAllBlogUser();

    void deleteUserbycontainerid(String containerid);
}