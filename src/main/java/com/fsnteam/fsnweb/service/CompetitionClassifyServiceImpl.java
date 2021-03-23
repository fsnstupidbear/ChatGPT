package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.CompetitionClassify;
import com.fsnteam.fsnweb.mapper.CompetitionClassifyMapper;
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
 * @since 2021-03-15
 */
@Service
public class CompetitionClassifyServiceImpl extends ServiceImpl<CompetitionClassifyMapper, CompetitionClassify> implements CompetitionClassifyService {

    @Autowired
    CompetitionClassifyService competitionClassifyService;

    @Override
    public Result getAllCompetitionClassifyList(Map params) {
        String competitionClassify = (String) params.get("competitionClassify");
        int current = (int)params.get("current");
        int size = (int) params.get("size");
        LambdaQueryWrapper<CompetitionClassify> queryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(competitionClassify)) {
            queryWrapper.like(CompetitionClassify::getCompetitionClassify, competitionClassify);
        }
        Page<CompetitionClassify> page = new Page<>(current, size);
        Page<CompetitionClassify>  competitionClassifyListPage = competitionClassifyService.page(page,queryWrapper);
        long total = competitionClassifyListPage.getTotal();
        List<CompetitionClassify> competitionClassifyList = competitionClassifyListPage.getRecords();
        return Result.success().data("competitionClassifyList",competitionClassifyList).data("total",total);
    }
}
