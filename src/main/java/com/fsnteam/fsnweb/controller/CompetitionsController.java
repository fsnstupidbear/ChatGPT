package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.service.CompetitionsService;
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
 * @since 2021-03-18
 */
@RestController
@RequestMapping("competitions")
public class CompetitionsController {

    @Autowired
    CompetitionsService competitionsService;

    @PostMapping("getAllCompetitions")
    public Result getAllCompetitions(@RequestBody Map params){
        return competitionsService.getAllCompetitions(params);
    }

    @PostMapping("updateSignUpStateById")
    public Result updateSignUpStateById(@RequestBody Map params){
        competitionsService.updateSignUpStateById(params);
        return Result.success();
    }

    @PostMapping("ifHasAuthority")
    public Result ifHasAuthority(){
        return competitionsService.ifHasAuthority();
    }

    @PostMapping("commitResult")
    public Result commitResult(@RequestBody Map params){
        return competitionsService.commitResult(params);
    }

    @PostMapping("insertCompetition")
    public Result insertCompetition(@RequestBody Map params){
        return competitionsService.insertCompetition(params);
    }

    @PostMapping("deleteCompetitionById")
    public Result deleteCompetitionById(@RequestBody Map params){
        return competitionsService.deleteCompetitionById(params);
    }
}

