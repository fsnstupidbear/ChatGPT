package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.entity.SelectMemberModelRecord;
import com.fsnteam.fsnweb.service.SelectMemberModelRecordService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 败方免单规则，1为固定击杀数，0为达到进行局数的百分比 前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2022-08-14
 */
@RestController
@RequestMapping("selectMemberModelRecord")
public class SelectMemberModelRecordController {

    @Autowired
    SelectMemberModelRecordService selectMemberModelRecordService;

    @RequestMapping("insertSelectMemberModelRecord")
    public Result insertSelectMemberModelRecord(@RequestBody Map params){
        return selectMemberModelRecordService.insertSelectMemberModelRecord(params);
    }

    @RequestMapping("selectMemberModelCheckScore")
    public Result selectMemberModelCheckScore(@RequestBody Map params){
        return selectMemberModelRecordService.selectMemberModelCheckScore(params);
    }

    @RequestMapping("getOnGoingCompetition")
    public Result getOnGoingCompetition(@RequestBody Map params){
        return selectMemberModelRecordService.getOnGoingCompetition(params);
    }

    @RequestMapping("getRecordBillInfo")
    public Result getRecordBillInfo(@RequestBody Map params){
        return selectMemberModelRecordService.getRecordBillInfo(params);
    }

    @RequestMapping("deleteCurrentSelectMemberModelRecord")
    public Result deleteCurrentSelectMemberModelRecord(@RequestBody Map params){
        return selectMemberModelRecordService.deleteCurrentSelectMemberModelRecord(params);
    }

    @PostMapping("ifHasCaptainAuthority")
    public Result ifHasCaptainAuthority(){
        return selectMemberModelRecordService.ifHasCaptainAuthority();
    }

    @PostMapping("getCurrentAccountID")
    public Result getCurrentAccountID(){
        return selectMemberModelRecordService.getCurrentAccountID();
    }

    @PostMapping("getFourVsFourSelectMemberModelHistory")
    public Result getFourVsFourSelectMemberModelHistory(@RequestBody Map params){
        return selectMemberModelRecordService.getFourVsFourSelectMemberModelHistory(params);
    }

    @PostMapping("resultPageDeleteCurrentRecord")
    public Result resultPageDeleteCurrentRecord(@RequestBody Map params){
        return selectMemberModelRecordService.resultPageDeleteCurrentRecord(params);
    }

    @PostMapping("getCurrentRoundPlayers")
    public Result getCurrentRoundPlayers(@RequestBody Map params){
        return selectMemberModelRecordService.getCurrentRoundPlayers(params);
    }
}

