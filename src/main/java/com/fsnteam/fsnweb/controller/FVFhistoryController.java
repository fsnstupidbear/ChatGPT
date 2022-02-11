package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.service.FVFhistoryService;
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
 * @since 2021-03-19
 */
@CrossOrigin
@RestController
@RequestMapping("fvfhistory")
public class FVFhistoryController {

    @Autowired
    FVFhistoryService fvFhistoryService;

    @PostMapping("getAllFVFHistory")
    public Result getAllFVFHistory(@RequestBody Map params){
        return fvFhistoryService.getAllFVFHistory(params);
    }

    @PostMapping("saveCurrentRound")
    public Result saveCurrentRound(@RequestBody Map params){
        fvFhistoryService.saveCurrentRound(params);
        return Result.success();
    }

    @PostMapping("deleteCurrentRecord")
    public Result deleteCurrentRecord(@RequestBody Map params){
        fvFhistoryService.removeById((String)params.get("id"));
        return Result.success();
    }
}

