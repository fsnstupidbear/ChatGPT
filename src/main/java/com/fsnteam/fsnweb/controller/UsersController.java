package com.fsnteam.fsnweb.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.handler.BussinessException;
import com.fsnteam.fsnweb.service.UsersService;
import com.fsnteam.fsnweb.util.Result;
import com.fsnteam.fsnweb.util.ReturnCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    @Autowired
    UsersService usersService;

    /**
     * 分页查询
     * @return
     */
    @PostMapping(value = "/getAllUsers",produces="application/json;charset=UTF-8")
    @ApiOperation(value = "查询全部用户",notes = "")
    public Result getAllUsers(@RequestBody Map params){
        //对用户进行分页，泛型中注入的为用户实体类
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        Page<Users> page = new Page<>(current,size);
        Page<Users> userspage = usersService.page(page);
        long total = userspage.getTotal();
        List<Users> records = userspage.getRecords();
        return Result.success().data("total",total).data("records",records);
    }

    @GetMapping("getUserById/{id}")
    @ApiOperation(value = "查询单个用户信息",notes = "通过id查询对应用户信息")
    public Result getUserById(@PathVariable("id") Long id){

        Users user = usersService.getById(id);
        if(user!=null) {
            return Result.success().data("user", user);
        }
        else {
            throw new BussinessException(ReturnCode.USER_NOT_FOUND.getCode(),ReturnCode.USER_NOT_FOUND.getMessage());
        }
    }
}

