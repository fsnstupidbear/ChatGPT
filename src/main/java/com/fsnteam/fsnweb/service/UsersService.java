package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2020-11-23
 */
public interface UsersService extends IService<Users> {
    /**
     * 根据条件取得用户列表
     * @return
     */
    public Result getAllUsers(Map params);

    Result getAllUsersNormalInfo(Map params);
}
