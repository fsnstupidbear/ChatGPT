package com.fsnteam.fsnweb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.Competitions;
import com.fsnteam.fsnweb.mapper.CompetitionsMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        queryWrapper.select(Competitions::getId,Competitions::getCompetitionName,Competitions::getStartDate,Competitions::getType,
                Competitions::getResult,Competitions::isCouldSignUp,Competitions::getSponsor,Competitions::getReward);
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

    @Override
    public Result updateSignUpStateById(Map params) {
        String competitionId = (String) params.get("id");
        boolean state = (Boolean) params.get("state");
        Competitions competitions = new Competitions();
        competitions.setId(competitionId);
        competitions.setCouldSignUp(state);
        competitionsService.updateById(competitions);
        return Result.success();
    }

    @Override
    public Result ifHasAuthority() {
        boolean ifHasAuthority = false;
        String needRole = "队长";
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(needRole)) {
                ifHasAuthority = true;
            }
        }
        return Result.success().data("ifHasAuthority",ifHasAuthority);
    }

    @Override
    public Result commitResult(Map params) {
        String competitionId = (String) params.get("id");
        String result = (String) params.get("result");
        Competitions competition = new Competitions();
        competition.setId(competitionId);
        competition.setResult(result);
        competitionsService.updateById(competition);
        return Result.success();
    }

    @Override
    public Result insertCompetition(Map params) {
        JSONObject jsonobject = (JSONObject) JSONObject.toJSON(params.get("competitionInfo"));
        Competitions competition = JSON.toJavaObject(jsonobject,Competitions.class);
        competitionsService.save(competition);
        return Result.success();
    }

    @Override
    public Result deleteCompetitionById(Map params) {
        String competitionId = (String) params.get("competitionId");
        competitionsService.removeById(competitionId);
        return Result.success();
    }
}
