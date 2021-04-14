package com.example.demo3.test.service;

import com.example.demo3.test.dao.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-12
 * @Content:
 */
public interface IUserService extends UserDetailsService {


    List<User> getUserList(String likename);
}
