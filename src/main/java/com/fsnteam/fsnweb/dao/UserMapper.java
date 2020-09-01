package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.User;
import com.fsnteam.fsnweb.bean.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据ID查找信息
    @Select("select username,password,role,isenabled,vocation from USERS where id=#{id}")
    User selectUserById(String id);

    //查找角色
    @Select("select id,name,role from ROLE where userid=#{id}")
    List<UserRole> selectRoleById(String id);
}
