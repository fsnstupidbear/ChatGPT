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
}

