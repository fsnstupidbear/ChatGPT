package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.mapper.UsersMapper;
import com.fsnteam.fsnweb.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
