package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    //根据ID查找信息
    @Select("select username,password,role,isenabled,vocation from USERS where id='10000'")
    User selectById(String id);
}
