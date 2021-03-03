package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.dao.UserMapper;
import com.fsnteam.fsnweb.entity.Role;
import com.fsnteam.fsnweb.entity.UserRoleRelation;
import com.fsnteam.fsnweb.service.RoleService;
import com.fsnteam.fsnweb.service.UserRoleRelationService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-02-24
 */
@RestController
@RequestMapping("userRole")
public class UserRoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleRelationService userRoleRelationService;

    @PostMapping("getAllRoles")
    public Result getAllRole(){
        List<Role> list = roleService.list();
        return Result.success().data("roleList",list);
    }

    @PostMapping("getRolesById")
    public Result getRolesById(@RequestBody Map params){
        String id = (String) params.get("id");
        String roles = userMapper.selectRoleById(id);
        String[] rolesList = new String[0];
        if(roles!=null||!roles.equals("")) {
            rolesList = roles.split(",");
        }
        return Result.success().data("rolesList",rolesList);
    }

    @PostMapping("updateRolesById")
    public Result updateRolesById(@RequestBody Map params){
        String id = (String) params.get("id");
        ArrayList roles = (ArrayList) params.get("roles");
        String rolesString="";
        for (int i = 0; i < roles.size(); i++) {
            rolesString = rolesString+roles.get(i);
            if(i+1<roles.size()){
                rolesString+=",";
            }
        }
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setUserid(id);
        userRoleRelation.setRole(rolesString);
        userRoleRelationService.updateById(userRoleRelation);
        return Result.success().tip("更新角色完成！");
    }
}
