package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.service.NewsService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-27
 */
@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @PostMapping("addNews")
    public Result addNews(@RequestBody Map params){
        return newsService.addNews(params);
    }

    @PostMapping("getNewsList")
    public Result getNewsList(@RequestBody Map params){
        return newsService.getNewsList(params);
    }

    @PostMapping("deleteNewsById")
    public Result deleteNewsById(@RequestBody Map params){
        return newsService.deleteNewsById(params);
    }

    @PostMapping("getNewsContentById")
    public Result getNewsContentById(@RequestBody Map params){
        return newsService.getNewsContentById(params);
    }

    @PostMapping("updateNews")
    public Result updateNews(@RequestBody Map params){
        return newsService.updateNews(params);
    }

    @PostMapping("getActivityRulesList")
    public Result getActivityRulesList(@RequestBody Map params){
        return newsService.getActivityRulesList(params);
    }

    @PostMapping("getNewestMessageList")
    public Result getNewestMessageList(){
        return newsService.getNewestMessageList();
    }
}

