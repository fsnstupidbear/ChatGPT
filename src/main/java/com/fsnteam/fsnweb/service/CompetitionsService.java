package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.entity.Competitions;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-18
 */
public interface CompetitionsService extends IService<Competitions> {

    public Result getAllCompetitions(Map params);
}
