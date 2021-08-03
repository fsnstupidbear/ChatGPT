package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.News;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.mapper.NewsMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-27
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    NewsService newsService;

    @Autowired
    UsersService usersService;

    @Override
    public Result addNews(Map params) {
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        String type = (String) params.get("type");
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setCreatedDate(new Date());
        news.setUpdateDate(new Date());
        news.setType(type);
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.getById(id);
        news.setAuthor(user.getUsername());
        newsService.save(news);
        return Result.success();
    }

    @Override
    public Result getNewsList(Map params) {
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        String title = (String)params.get("title");
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(title)){
            queryWrapper.like(News::getTitle,title);
        }
        queryWrapper.eq(News::getType,"1");
        Page<News> page = new Page<>(current,size);
        Page<News> newspage = newsService.page(page,queryWrapper);
        long total = newspage.getTotal();
        List<News> records = newspage.getRecords();
        return Result.success().data("total",total).data("newsList",records);
    }

    @Override
    public Result deleteNewsById(Map params) {
        String id = (String)params.get("id");
        newsService.removeById(id);
        return Result.success();
    }

    @Override
    public Result getNewsContentById(Map params) {
        String id = (String) params.get("id");
        News news = newsService.getById(id);
        return Result.success().data("news",news);
    }

    @Override
    public Result updateNews(Map params) {
        News news = new News();
        String id = (String) params.get("id");
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        news.setContent(content);
        news.setTitle(title);
        news.setId(id);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.getById(userId);
        news.setAuthor(user.getUsername());
        news.setUpdateDate(new Date());
        newsService.updateById(news);
        return Result.success();
    }

    @Override
    public Result getActivityRulesList(Map params) {
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        String title = (String)params.get("title");
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(title)){
            queryWrapper.like(News::getTitle,title);
        }
        queryWrapper.eq(News::getType,"2");
        Page<News> page = new Page<>(current,size);
        Page<News> newspage = newsService.page(page,queryWrapper);
        long total = newspage.getTotal();
        List<News> records = newspage.getRecords();
        return Result.success().data("total",total).data("newsList",records);
    }

    @Override
    public Result getNewestMessageList() {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        Page<News> page = new Page<>(1,5);
        Page<News> newspage = newsService.page(page,queryWrapper);
        List<News> records = newspage.getRecords();
        return Result.success().data("messageList",records);
    }
}
