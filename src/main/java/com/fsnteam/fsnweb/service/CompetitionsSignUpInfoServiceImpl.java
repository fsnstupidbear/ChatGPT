package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.CompetitionsSignUpInfo;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.mapper.CompetitionsSignUpInfoMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-20
 */
@Service
public class CompetitionsSignUpInfoServiceImpl extends ServiceImpl<CompetitionsSignUpInfoMapper, CompetitionsSignUpInfo> implements CompetitionsSignUpInfoService {

    @Autowired
    UsersService usersService;

    @Autowired
    CompetitionsSignUpInfoService competitionsSignUpInfoService;

    @Override
    public Result addSignUpInfo(Map params) {
        String competitionId = (String) params.get("competitionId");
        CompetitionsSignUpInfo competitionsSignUpInfo = new CompetitionsSignUpInfo();
        competitionsSignUpInfo.setCompetitionId(competitionId);
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        competitionsSignUpInfo.setUserId(id);
        Users user = usersService.getById(id);
        String username = user.getUsername();
        competitionsSignUpInfo.setUsername(username);
        competitionsSignUpInfoService.save(competitionsSignUpInfo);
        return Result.success();
    }

    @Override
    public Result getSignUpInfo(Map params) {
        String competitionId = (String) params.get("competitionId");
        LambdaQueryWrapper<CompetitionsSignUpInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(CompetitionsSignUpInfo::getUserId,CompetitionsSignUpInfo::getUsername);
        if(competitionId!=null) {
            if(StringUtils.isNotBlank(competitionId)){
                queryWrapper.eq(CompetitionsSignUpInfo::getCompetitionId,competitionId);
            }
        }
        List<CompetitionsSignUpInfo> competitionsSignUpInfos = competitionsSignUpInfoService.list(queryWrapper);
        return Result.success().data("competitionsSignUpInfos",competitionsSignUpInfos);
    }
}
