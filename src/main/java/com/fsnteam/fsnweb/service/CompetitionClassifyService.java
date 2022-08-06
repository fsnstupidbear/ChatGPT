package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.CompetitionClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-15
 */
public interface CompetitionClassifyService extends IService<CompetitionClassify> {

    //获取赛事类别
    public Result getAllCompetitionClassifyList(Map params);

    Result getAllCompetitionClassify();
}
