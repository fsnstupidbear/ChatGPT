package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.Role;
import com.fsnteam.fsnweb.mapper.RoleMapper;
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
 * @since 2021-03-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleService roleService;

    @Override
    public Result getAllRoles(String role,int current,int size) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(role)) {
            queryWrapper.like(Role::getRole, role);
        }
        Page<Role> page = new Page<>(current, size);
        Page<Role>  rolePage = roleService.page(page,queryWrapper);
        long total = rolePage.getTotal();
        List<Role> rolesList = rolePage.getRecords();
        return Result.success().data("rolesList",rolesList).data("total",total);
    }

    @Override
    public Result disabledRole(Map params) {
        Role role = new Role();
        role.setRole((String) params.get("id"));
        role.setDisabled(false);
        roleService.updateById(role);
        return Result.success();
    }
}
