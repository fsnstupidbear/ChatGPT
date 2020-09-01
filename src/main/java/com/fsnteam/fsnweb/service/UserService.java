package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.User;
import com.fsnteam.fsnweb.bean.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    //查询角色
    List<UserRole> selectRoleById(String id);

    //查询用户
    User selectUserById(String id);
}
