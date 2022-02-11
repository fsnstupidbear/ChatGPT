package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.service.RoleService;
import com.fsnteam.fsnweb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-03
 */
@CrossOrigin
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("getAllRoles")
    @ApiOperation(value = "获取角色列表")
    public Result getAllRoles(@RequestBody Map params){
        String role = (String) params.get("role");
        int current = (int)params.get("current");
        int size = (int) params.get("size");
        return roleService.getAllRoles(role,current,size);
    }
}

