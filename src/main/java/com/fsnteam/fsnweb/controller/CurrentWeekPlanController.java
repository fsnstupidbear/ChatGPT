package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.entity.CurrentWeekPlan;
import com.fsnteam.fsnweb.service.CurrentWeekPlanService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-23
 */
@RestController
@RequestMapping("weekPlan")
public class CurrentWeekPlanController {

    @Autowired
    CurrentWeekPlanService currentWeekPlanService;

    @PostMapping("getWeekPlan")
    public Result getWeekPlan(){
        List<CurrentWeekPlan> list =currentWeekPlanService.list();
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK)-1;
        return Result.success().data("weekPlan",list).data("weekday",weekday);
    }


    @PostMapping("updatePlanById")
    public Result updatePlanById(@RequestBody Map params){
        String id = (String) params.get("id");
        String plan = (String) params.get("plan");
        CurrentWeekPlan currentWeekPlan = new CurrentWeekPlan();
        currentWeekPlan.setId(id);
        currentWeekPlan.setPlan(plan);
        currentWeekPlanService.updateById(currentWeekPlan);
        return Result.success();
    }
}

