package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.User;
import com.fsnteam.fsnweb.bean.UserRole;
import com.fsnteam.fsnweb.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据ID查询角色
     * @param id
     * @return
     */
    @Override
    public List<UserRole> selectRoleById(String id) {
        return userMapper.selectRoleById(id);
    }

    /**
     *根据ID查询用户
     * @param id
     * @return
     */
    @Override
    public User selectUserById(String id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 添加普通队员
     * @param user
     */
    @Override
    public void insertMember(User user) {
        userMapper.insertUser(user);
        UserRole userRole=new UserRole();
        userRole.setUserID(user.getId());
        userRole.setRole("ROLE_MEMBER");
        userMapper.insertRole(userRole);
    }
}
