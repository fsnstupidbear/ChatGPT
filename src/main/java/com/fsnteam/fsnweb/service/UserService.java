package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    //查询角色
    String selectRoleById(String id);

    //查询用户
    User selectUserById(String id);

    //添加队员
    void insertMember(User user);

    //返回最新自增变量

}
