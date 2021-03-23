package com.fsnteam.fsnweb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.mapper.UsersMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2020-11-23
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    UsersService usersService;

    @Override
    public Result getAllUsers(Map params) {
        //对用户进行分页，泛型中注入的为用户实体类
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        JSONObject jsonobject = JSONObject.parseObject((String) params.get("users"));
        Users users = JSON.toJavaObject(jsonobject,Users.class);
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Users::getId,Users::getUsername,Users::getVocation,Users::getDepartment,Users::getJoinDate,
                Users::getQQNumber,Users::getPhoneNumber,Users::getIsEnabled);
        if(users!=null) {
            if(StringUtils.isNotBlank(users.getUsername())){
                queryWrapper.like(Users::getUsername,users.getUsername());
            }
            if(StringUtils.isNotBlank(users.getVocation())){
                queryWrapper.eq(Users::getVocation,users.getVocation());
            }
            if(StringUtils.isNotBlank(users.getDepartment())){
                queryWrapper.eq(Users::getDepartment,users.getDepartment());
            }
        }
        Page<Users> page = new Page<>(current,size);
        Page<Users> userspage = usersService.page(page,queryWrapper);
        long total = userspage.getTotal();
        List<Users> records = userspage.getRecords();
        return Result.success().data("total",total).data("records",records);
    }
}
