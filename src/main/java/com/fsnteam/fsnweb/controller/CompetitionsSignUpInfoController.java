package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.service.CompetitionsSignUpInfoService;
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
 * @since 2021-03-20
 */
@RestController
@RequestMapping("competitionsSignUpInfo")
public class CompetitionsSignUpInfoController {

    @Autowired
    CompetitionsSignUpInfoService competitionsSignUpInfoService;

    @PostMapping("addSignUpInfo")
    public Result addSignUpInfo(@RequestBody Map params){
        return competitionsSignUpInfoService.addSignUpInfo(params);
    }

    @PostMapping("getSignUpInfo")
    public Result getSignUpInfo(@RequestBody Map params){
        return competitionsSignUpInfoService.getSignUpInfo(params);
    }

    @PostMapping("cancelSignUp")
    public Result cancelSignUp(@RequestBody Map params){
        return competitionsSignUpInfoService.cancelSignUp(params);
    }
}

