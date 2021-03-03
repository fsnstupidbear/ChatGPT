package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.Role;
import com.fsnteam.fsnweb.mapper.RoleMapper;
import com.fsnteam.fsnweb.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-02-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
