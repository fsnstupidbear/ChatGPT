package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.entity.CompetitionClassify;
import com.fsnteam.fsnweb.service.CompetitionClassifyService;
import com.fsnteam.fsnweb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-15
 */
@RestController
@CrossOrigin
@RequestMapping("competitionClassify")
public class CompetitionClassifyController {

    @Autowired
    CompetitionClassifyService competitionClassifyService;

    @PostMapping("getAllCompetitionClassifyList")
    @ApiOperation("获取比赛类别列表")
    public Result getAllCompetitionClassifyList(@RequestBody Map params){
        return competitionClassifyService.getAllCompetitionClassifyList(params);
    }

    @PostMapping("deleteCompetitionClassifyById")
    public Result deleteCompetitionClassifyById(@RequestBody Map params){
        competitionClassifyService.removeById((Integer)params.get("id"));
        return Result.success();
    }

    @PostMapping("addCompetitionClassify")
    public Result addCompetitionClassify(@RequestBody Map params){
        CompetitionClassify competitionClassify = new CompetitionClassify();
        competitionClassify.setCompetitionClassify((String)params.get("competitionClassify"));
        competitionClassifyService.save(competitionClassify);
        return Result.success();
    }

    @PostMapping("getAllCompetitionClassify")
    public Result getAllCompetitionClassify(){
        return competitionClassifyService.getAllCompetitionClassify();
    }
}
