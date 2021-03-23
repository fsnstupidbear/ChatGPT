package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.Competitions;
import com.fsnteam.fsnweb.mapper.CompetitionsMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-18
 */
@Service
public class CompetitionsServiceImpl extends ServiceImpl<CompetitionsMapper, Competitions> implements CompetitionsService {

    @Autowired
    CompetitionsService competitionsService;

    @Override
    public Result getAllCompetitions(Map params) {
        //对用户进行分页，泛型中注入的为用户实体类
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        String competitionName = (String) params.get("competitionName");
        LambdaQueryWrapper<Competitions> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Competitions::getId,Competitions::getCompetitionName,Competitions::getDate,Competitions::getResult);
        if(competitionName!=null) {
            if(StringUtils.isNotBlank(competitionName)){
                queryWrapper.like(Competitions::getCompetitionName,competitionName);
            }
        }
        Page<Competitions> page = new Page<>(current,size);
        Page<Competitions> competitionspage = competitionsService.page(page,queryWrapper);
        long total = competitionspage.getTotal();
        List<Competitions> records = competitionspage.getRecords();
        return Result.success().data("total",total).data("competitions",records);
    }
}
