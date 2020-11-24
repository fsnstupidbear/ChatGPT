package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.handler.BussinessException;
import com.fsnteam.fsnweb.handler.BussinessException;
import com.fsnteam.fsnweb.service.UsersService;
import com.fsnteam.fsnweb.util.Result;
import com.fsnteam.fsnweb.util.ReturnCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("/getAllUsers")
    @ApiOperation(value = "查询全部用户",notes = "")
    public Result getAllUsers(){
        List<Users> usersList = usersService.list();
        return Result.success().data("users",usersList);
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

