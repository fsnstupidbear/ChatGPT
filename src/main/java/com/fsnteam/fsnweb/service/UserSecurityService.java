package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.User;
import com.fsnteam.fsnweb.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user= userMapper.selectById(id);
        return user;
    }
}
