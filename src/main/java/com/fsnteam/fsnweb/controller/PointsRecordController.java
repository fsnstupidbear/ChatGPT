package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.service.PointsRecordService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-26
 */
@CrossOrigin
@RestController
@RequestMapping("/pointsRecord")
public class PointsRecordController {

    @Autowired
    PointsRecordService pointsRecordService;

    @PostMapping("insertPointsRecordById")
    public Result insertPointsRecordById(@RequestBody Map params){
        return pointsRecordService.insertPointsRecordById(params);
    }

    @PostMapping("getMyPointsChartCurrentMonthData")
    public Result getMyPointsChartCurrentMonthData(Map params){
        return pointsRecordService.getMyPointsChartCurrentMonthData(params);
    }
}
