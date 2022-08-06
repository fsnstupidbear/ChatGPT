package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.entity.PlanList;
import com.fsnteam.fsnweb.service.PlanListService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-23
 */
@CrossOrigin
@RestController
@RequestMapping("trainingPlanList")
public class PlanListController {

    @Autowired
    PlanListService planListService;

    @PostMapping("getPlans")
    public Result getPlans(){
        List<PlanList> list = planListService.list();
        return Result.success().data("planList",list);
    }

}

