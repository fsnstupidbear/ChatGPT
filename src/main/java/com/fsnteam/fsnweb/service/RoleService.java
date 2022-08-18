package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-03
 */
public interface RoleService extends IService<Role> {
       Result getAllRoles(String role,int current,int size);

       Result disabledRole(Map params);
}
