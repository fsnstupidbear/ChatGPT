package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.FVFhistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-19
 */
public interface FVFhistoryService extends IService<FVFhistory> {

    public Result getAllFVFHistory(Map params);

    public void saveCurrentRound(Map params);
}
