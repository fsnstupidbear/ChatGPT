package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public class IUsersServiceImpl implements IUsersService {

    UsersService usersService;

    @Override
    public Result getAllUsers(Map params) {
        //对用户进行分页，泛型中注入的为用户实体类
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        String ID = (String)params.get("ID");
        String vocation = (String)params.get("vocation");
        String department = (String)params.get("department");
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(ID)){
            queryWrapper.eq(Users::getId,ID);
        }
        if(StringUtils.isNotBlank(vocation)){
            queryWrapper.eq(Users::getVocation,vocation);
        }
        if(StringUtils.isNotBlank(department)){
            queryWrapper.eq(Users::getDepartment,department);
        }
        Page<Users> page = new Page<>(current,size);
        Page<Users> userspage = usersService.page(page);
        long total = userspage.getTotal();
        List<Users> records = userspage.getRecords();
        return Result.success().data("total",total).data("records",records);
    }
}
