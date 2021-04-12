package com.example.demo3.test.service.imp;

import com.example.demo3.test.dao.User;
import com.example.demo3.test.dao.UserMapper;
import com.example.demo3.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Version 1.0
 * @Author:zengtao
 * @Date:2021-4-12
 * @Content:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user==null){
            user = new User();
        }
        return user;
    }
}
