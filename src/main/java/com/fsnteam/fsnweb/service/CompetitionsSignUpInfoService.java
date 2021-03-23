package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.CompetitionsSignUpInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-20
 */
public interface CompetitionsSignUpInfoService extends IService<CompetitionsSignUpInfo> {

    public Result addSignUpInfo(Map params);

    public Result getSignUpInfo(Map params);
}
