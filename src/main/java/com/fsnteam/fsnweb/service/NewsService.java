package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-27
 */
public interface NewsService extends IService<News> {

    Result addNews(Map params);

    Result getNewsList(Map params);

    Result deleteNewsById(Map params);

    Result getNewsContentById(Map params);

    Result updateNews(Map params);

    Result getActivityRulesList(Map params);

    Result getNewestMessageList();
}
