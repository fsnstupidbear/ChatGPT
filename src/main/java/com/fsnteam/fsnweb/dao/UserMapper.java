package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.User;
import com.fsnteam.fsnweb.bean.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据ID查找信息
    @Select("select username,password,isenabled,vocation from USERS where id=#{id}")
    User selectUserById(String id);

    //查找角色
    @Select("select id,role from ROLE where userid=#{id}")
    List<UserRole> selectRoleById(String id);

    //添加队员
    @Insert("insert into USERS(username,vocation) values(#{username},#{vocation});")
    @Options(useGeneratedKeys=true,keyColumn="ID",keyProperty = "id")
    void insertUser(User user);

    //添加角色
    @Insert("insert into ROLE(role,USERID) values(#{role},#{userID})")
    void insertRole(UserRole userRole);
}
