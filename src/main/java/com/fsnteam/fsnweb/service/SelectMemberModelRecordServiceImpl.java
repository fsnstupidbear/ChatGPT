package com.fsnteam.fsnweb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsnteam.fsnweb.entity.*;
import com.fsnteam.fsnweb.mapper.SelectMemberModelRecordMapper;
import com.fsnteam.fsnweb.service.SelectMemberModelRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.util.CalDouble;
import com.fsnteam.fsnweb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 败方免单规则，1为固定击杀数，0为达到进行局数的百分比 服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2022-08-14
 */
@Service
public class SelectMemberModelRecordServiceImpl extends ServiceImpl<SelectMemberModelRecordMapper, SelectMemberModelRecord> implements SelectMemberModelRecordService {

    @Autowired
    SelectMemberModelRecordService selectMemberModelRecordService;

    @Autowired
    UsersService usersService;

    @Autowired
    FvfService fvfService;

    @Override
    public Result insertSelectMemberModelRecord(Map params) {
        JSONObject receiveData = JSONObject.parseObject(JSON.toJSONString(params.get("formData")));
        SelectMemberModelRecord selectMemberModelRecord = JSON.toJavaObject(receiveData, SelectMemberModelRecord.class);
        selectMemberModelRecord.setSponsor((String) receiveData.get("sponsor"));
        selectMemberModelRecord.setSponsorshipFunds(Integer.valueOf((String) receiveData.get("sponsorshipFunds")));
        //传入败方免单的方式，并根据方式选择传入需要计算的数据
        String isUseFixedKillNum = (String) receiveData.get("isUseFixedKillNum");
        selectMemberModelRecord.setFreeChargeRuleMode(isUseFixedKillNum);
        if (isUseFixedKillNum.equals("1")) {
            selectMemberModelRecord.setComputeKillBaseNum(Integer.valueOf((String) receiveData.get("fixedKillNum")));
        } else if (isUseFixedKillNum.equals("0")) {
            selectMemberModelRecord.setComputeKillBaseNum(Integer.valueOf((String) receiveData.get("baseOnRoundSumNum")));
        }
        //设置状态为正在进行中
        selectMemberModelRecord.setIsOnGoing("1");
        selectMemberModelRecord.setMember0((String) receiveData.get("attendPlayer0"));
        selectMemberModelRecord.setMember1((String) receiveData.get("attendPlayer1"));
        selectMemberModelRecord.setMember2((String) receiveData.get("attendPlayer2"));
        selectMemberModelRecord.setMember3((String) receiveData.get("attendPlayer3"));
        selectMemberModelRecord.setMember4((String) receiveData.get("attendPlayer4"));
        selectMemberModelRecord.setMember5((String) receiveData.get("attendPlayer5"));
        selectMemberModelRecord.setMember6((String) receiveData.get("attendPlayer6"));
        selectMemberModelRecord.setMember7((String) receiveData.get("attendPlayer7"));
        //获取当前操作人的ID
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Users byId = usersService.getById(id);
        String username = byId.getUsername();
        //记录操作人用户名及ID
        selectMemberModelRecord.setInitiator(username);
        selectMemberModelRecord.setInitiatorID(id);
        //记录操作时间
        selectMemberModelRecord.setStartDate(new Date());
        //随机生成选出队长
        Integer[] integers = fvfService.randNums(8);
        //循环体中找出0排在第几位，用于查找arraylist
        Integer captainNum = 0;
        for (int i = 0; i < integers.length; i++) {
            if (integers[i] == 0) {
                captainNum = i;
                break;
            }
        }
        String randomCaptainString = "attendPlayer" + captainNum;
        selectMemberModelRecord.setRandomCaptain((String) receiveData.get(randomCaptainString));
        selectMemberModelRecordService.save(selectMemberModelRecord);
        String selectMemberModelRecordId = selectMemberModelRecord.getID();
        return Result.success().data("selectMemberModelRecordId", selectMemberModelRecordId).data("randomCaptain", receiveData.get(randomCaptainString));
    }

    @Override
    public Result selectMemberModelCheckScore(Map params) {
        Map receiveData = (Map) params.get("formData");
        String selectMemberModelRecordId = (String) params.get("selectMemberModelRecordId");
        SelectMemberModelRecord currentRoundInfo = selectMemberModelRecordService.getById(selectMemberModelRecordId);
        //赞助资金
        double sponsorshipFunds = currentRoundInfo.getSponsorshipFunds();
        //败方需要支付资金
        double failedTeamNeedPay = currentRoundInfo.getFailedTeamNeedPay();
        //需要计算败方哪些人可以免单
        String freeChargeRuleMode = currentRoundInfo.getFreeChargeRuleMode();
        int computeKillBaseNum = currentRoundInfo.getComputeKillBaseNum();
        //计算总进行场次
        int firstRank = Integer.parseInt((String) receiveData.get("firstRank"));
        int secondRank = Integer.parseInt((String) receiveData.get("secondRank"));
        int sumRounds = firstRank + secondRank;
        double KillNumIndex = 0;
        if (freeChargeRuleMode.equals("1")) {
            KillNumIndex = computeKillBaseNum;
        } else if (freeChargeRuleMode.equals("0")) {
            KillNumIndex = CalDouble.mul(CalDouble.div(computeKillBaseNum,100,10),sumRounds) ;
        }
        //获取前端输入胜方败方名单及单人击杀数的数据
        String winnerList0 = (String) receiveData.get("winnerList0");
        String winnerList1 = (String) receiveData.get("winnerList1");
        String winnerList2 = (String) receiveData.get("winnerList2");
        String winnerList3 = (String) receiveData.get("winnerList3");
        String winnerKillList0 = (String) receiveData.get("winnerKillList0");
        String winnerKillList1 = (String) receiveData.get("winnerKillList1");
        String winnerKillList2 = (String) receiveData.get("winnerKillList2");
        String winnerKillList3 = (String) receiveData.get("winnerKillList3");
        String loserList0 = (String) receiveData.get("loserList0");
        String loserList1 = (String) receiveData.get("loserList1");
        String loserList2 = (String) receiveData.get("loserList2");
        String loserList3 = (String) receiveData.get("loserList3");
        String loserKillList0 = (String) receiveData.get("loserKillList0");
        String loserKillList1 = (String) receiveData.get("loserKillList1");
        String loserKillList2 = (String) receiveData.get("loserKillList2");
        String loserKillList3 = (String) receiveData.get("loserKillList3");
        //计算胜方账单
        List<SelectMemberModelPlayer> winnerPlayers = new ArrayList<>();
        //把胜方名单及个人击杀数加入列表
        SelectMemberModelPlayer winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(winnerList0);
        winnerPlayer.setKillNum(winnerKillList0);
        winnerPlayers.add(winnerPlayer);
        winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(winnerList1);
        winnerPlayer.setKillNum(winnerKillList1);
        winnerPlayers.add(winnerPlayer);
        winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(winnerList2);
        winnerPlayer.setKillNum(winnerKillList2);
        winnerPlayers.add(winnerPlayer);
        winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(winnerList3);
        winnerPlayer.setKillNum(winnerKillList3);
        winnerPlayers.add(winnerPlayer);
        //计算败方账单
        List<SelectMemberModelPlayer> loserPlayers = new ArrayList<>();
        //把败方名单及个人击杀数加入列表
        SelectMemberModelPlayer loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(loserList0);
        loserPlayer.setKillNum(loserKillList0);
        loserPlayers.add(loserPlayer);
        loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(loserList1);
        loserPlayer.setKillNum(loserKillList1);
        loserPlayers.add(loserPlayer);
        loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(loserList2);
        loserPlayer.setKillNum(loserKillList2);
        loserPlayers.add(loserPlayer);
        loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(loserList3);
        loserPlayer.setKillNum(loserKillList3);
        loserPlayers.add(loserPlayer);
        //计算胜方账单

        //计算胜方总击杀数
        int winnerSumKillNum = 0;
        double currentPlayerCoefficient = 0;
        for (int i = 0; i < winnerPlayers.size(); i++) {
            winnerSumKillNum += Integer.parseInt(winnerPlayers.get(i).getKillNum());
        }
        //计算胜方账单
        for (int i = 0; i < winnerPlayers.size(); i++) {
            currentPlayerCoefficient = CalDouble.div(Double.parseDouble(winnerPlayers.get(i).getKillNum()), winnerSumKillNum, 10);
            winnerPlayers.get(i).setBill("+"+String.format("%.2f",CalDouble.mul(currentPlayerCoefficient,sponsorshipFunds+failedTeamNeedPay)));
        }
        //计算败方账单

        //标记免单败方人员
        for (int i = 0; i < loserPlayers.size(); i++) {
            //如果击杀数超过指标，则免除账单
            if(Double.parseDouble(loserPlayers.get(i).getKillNum())>=KillNumIndex){
                loserPlayers.get(i).setNeedToPay(false);
            }
        }
        //计算败方需要支付人员的总击杀数
        int loserSumKillNum = 0;
        for (int i = 0; i < loserPlayers.size(); i++) {
            if(loserPlayers.get(i).isNeedToPay()!=false) {
                loserSumKillNum += Integer.parseInt(loserPlayers.get(i).getKillNum());
            }
        }
        //计算败方需要支付人员的账单系数
        for (int i = 0; i < loserPlayers.size(); i++) {
            //如果属性是不需要支付，设置账单为0
            if(loserPlayers.get(i).isNeedToPay()==false) {
                loserPlayers.get(i).setBill("0.00");
            }else {
                loserPlayers.get(i).setCurrentLoserPlayerCoefficient
                        (CalDouble.div(loserSumKillNum,Double.parseDouble
                                (loserPlayers.get(i).getKillNum()),10));
            }
        }
        //计算败方系数总和
        double sumLoserPlayerCoefficient = 0;
        for (int i = 0; i < loserPlayers.size(); i++) {
            sumLoserPlayerCoefficient += loserPlayers.get(i).getCurrentLoserPlayerCoefficient();
        }
        //根据上一步算出的系数计算败方账单
        for (int i = 0; i < loserPlayers.size(); i++) {
            if(loserPlayers.get(i).isNeedToPay()) {
                loserPlayers.get(i).setBill("-" + String.format("%.2f",CalDouble.mul(CalDouble.div(loserPlayers.get(i).getCurrentLoserPlayerCoefficient(),sumLoserPlayerCoefficient,5), failedTeamNeedPay)));
            }
        }

        //把计算完成的数据存入数据库
        SelectMemberModelRecord selectMemberModelRecord = new SelectMemberModelRecord();
        selectMemberModelRecord.setWinner0(winnerPlayers.get(0).getUsername());
        selectMemberModelRecord.setWinner1(winnerPlayers.get(1).getUsername());
        selectMemberModelRecord.setWinner2(winnerPlayers.get(2).getUsername());
        selectMemberModelRecord.setWinner3(winnerPlayers.get(3).getUsername());
        selectMemberModelRecord.setWinner0Kill(winnerPlayers.get(0).getKillNum());
        selectMemberModelRecord.setWinner1Kill(winnerPlayers.get(1).getKillNum());
        selectMemberModelRecord.setWinner2Kill(winnerPlayers.get(2).getKillNum());
        selectMemberModelRecord.setWinner3Kill(winnerPlayers.get(3).getKillNum());
        selectMemberModelRecord.setWinnerBill0(winnerPlayers.get(0).getBill());
        selectMemberModelRecord.setWinnerBill1(winnerPlayers.get(1).getBill());
        selectMemberModelRecord.setWinnerBill2(winnerPlayers.get(2).getBill());
        selectMemberModelRecord.setWinnerBill3(winnerPlayers.get(3).getBill());
        selectMemberModelRecord.setLoser0(loserPlayers.get(0).getUsername());
        selectMemberModelRecord.setLoser1(loserPlayers.get(1).getUsername());
        selectMemberModelRecord.setLoser2(loserPlayers.get(2).getUsername());
        selectMemberModelRecord.setLoser3(loserPlayers.get(3).getUsername());
        selectMemberModelRecord.setLoser0Kill(loserPlayers.get(0).getKillNum());
        selectMemberModelRecord.setLoser1Kill(loserPlayers.get(1).getKillNum());
        selectMemberModelRecord.setLoser2Kill(loserPlayers.get(2).getKillNum());
        selectMemberModelRecord.setLoser3Kill(loserPlayers.get(3).getKillNum());
        selectMemberModelRecord.setLoserBill0(loserPlayers.get(0).getBill());
        selectMemberModelRecord.setLoserBill1(loserPlayers.get(1).getBill());
        selectMemberModelRecord.setLoserBill2(loserPlayers.get(2).getBill());
        selectMemberModelRecord.setLoserBill3(loserPlayers.get(3).getBill());
        //存入比分数据
        String score =firstRank+":"+secondRank;
        selectMemberModelRecord.setScore(score);
        //把是否正在进行状态改为0
        selectMemberModelRecord.setIsOnGoing("0");
        selectMemberModelRecord.setID((String) params.get("selectMemberModelRecordId"));
        selectMemberModelRecord.setEndDate(new Date());
        //记录两个比分
        selectMemberModelRecord.setFirstRank(String.valueOf(firstRank));
        selectMemberModelRecord.setSecondRank(String.valueOf(secondRank));

        //拼装胜方阵容名单
        String winnerPlayersString = "";
        for (int i = 0; i < winnerPlayers.size(); i++) {
            winnerPlayersString += winnerPlayers.get(i).getUsername();
            if(i < winnerPlayers.size()-1){
                winnerPlayersString += " ";
            }
        }
        selectMemberModelRecord.setWinnerPlayers(winnerPlayersString);
        //拼装败方阵容名单
        String loserPlayersString = "";
        for (int i = 0; i < loserPlayers.size(); i++) {
            loserPlayersString += loserPlayers.get(i).getUsername();
            if(i < loserPlayers.size()-1){
                loserPlayersString += " ";
            }
        }
        selectMemberModelRecord.setLoserPlayers(loserPlayersString);
        //存入数据库
        selectMemberModelRecordService.updateById(selectMemberModelRecord);
        return Result.success().data("winnerList",winnerPlayers).data("loserList",loserPlayers);
    }

    @ApiOperation("获取正在进行的点将模式4V4信息")
    @Override
    public Result getOnGoingCompetition(Map params) {
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        LambdaQueryWrapper<SelectMemberModelRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SelectMemberModelRecord::getIsOnGoing,"1");
        queryWrapper.select(SelectMemberModelRecord::getID,SelectMemberModelRecord::getSponsor,SelectMemberModelRecord::getStartDate,
                SelectMemberModelRecord::getInitiator,SelectMemberModelRecord::getMember0,SelectMemberModelRecord::getMember1,
                SelectMemberModelRecord::getMember2,SelectMemberModelRecord::getMember3,SelectMemberModelRecord::getMember4,
                SelectMemberModelRecord::getMember5,SelectMemberModelRecord::getMember6,SelectMemberModelRecord::getMember7
        ,SelectMemberModelRecord::getInitiatorID,SelectMemberModelRecord::getRandomCaptain);
        //按照日期倒序排序
        queryWrapper.orderByDesc(SelectMemberModelRecord::getStartDate);
        Page<SelectMemberModelRecord> selectMemberModelRecordPage= new Page<>(current,size);
        selectMemberModelRecordPage = selectMemberModelRecordService.page(selectMemberModelRecordPage, queryWrapper);
        List<SelectMemberModelRecord> records = selectMemberModelRecordPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setPlayerLineup(records.get(i).getMember0()+" "+records.get(i).getMember1()+" "+records.get(i).getMember2()+" "+
                    records.get(i).getMember3()+" "+records.get(i).getMember4()+" "+records.get(i).getMember5()+" "+records.get(i).getMember6()+" "+
                    records.get(i).getMember7());
        }
        //将胜方败方及各选手账单以及击杀数存入数据库
        long total = selectMemberModelRecordPage.getTotal();
        return Result.success().data("selectMemberModelRecordList",records).data("total",total);
    }

    @ApiOperation("获取账单信息")
    @Override
    public Result getRecordBillInfo(Map params) {
        String competitionID = (String) params.get("competitionID");
        SelectMemberModelRecord selectMemberModelRecord = selectMemberModelRecordService.getById(competitionID);
        List<SelectMemberModelPlayer> loserPlayers = new ArrayList<>();
        SelectMemberModelPlayer loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(selectMemberModelRecord.getLoser0());
        loserPlayer.setKillNum(selectMemberModelRecord.getLoser0Kill());
        loserPlayer.setBill(selectMemberModelRecord.getLoserBill0());
        loserPlayers.add(loserPlayer);
        loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(selectMemberModelRecord.getLoser1());
        loserPlayer.setKillNum(selectMemberModelRecord.getLoser1Kill());
        loserPlayer.setBill(selectMemberModelRecord.getLoserBill1());
        loserPlayers.add(loserPlayer);
        loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(selectMemberModelRecord.getLoser2());
        loserPlayer.setKillNum(selectMemberModelRecord.getLoser2Kill());
        loserPlayer.setBill(selectMemberModelRecord.getLoserBill2());
        loserPlayers.add(loserPlayer);
        loserPlayer = new SelectMemberModelPlayer();
        loserPlayer.setUsername(selectMemberModelRecord.getLoser3());
        loserPlayer.setKillNum(selectMemberModelRecord.getLoser3Kill());
        loserPlayer.setBill(selectMemberModelRecord.getLoserBill3());
        loserPlayers.add(loserPlayer);
        //拼接胜方数据
        List<SelectMemberModelPlayer> winnerPlayers = new ArrayList<>();
        SelectMemberModelPlayer winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(selectMemberModelRecord.getWinner0());
        winnerPlayer.setKillNum(selectMemberModelRecord.getWinner0Kill());
        winnerPlayer.setBill(selectMemberModelRecord.getWinnerBill0());
        winnerPlayers.add(winnerPlayer);
        winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(selectMemberModelRecord.getWinner1());
        winnerPlayer.setKillNum(selectMemberModelRecord.getWinner1Kill());
        winnerPlayer.setBill(selectMemberModelRecord.getWinnerBill1());
        winnerPlayers.add(winnerPlayer);
        winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(selectMemberModelRecord.getWinner2());
        winnerPlayer.setKillNum(selectMemberModelRecord.getWinner2Kill());
        winnerPlayer.setBill(selectMemberModelRecord.getWinnerBill2());
        winnerPlayers.add(winnerPlayer);
        winnerPlayer = new SelectMemberModelPlayer();
        winnerPlayer.setUsername(selectMemberModelRecord.getWinner3());
        winnerPlayer.setKillNum(selectMemberModelRecord.getWinner3Kill());
        winnerPlayer.setBill(selectMemberModelRecord.getWinnerBill3());
        winnerPlayers.add(winnerPlayer);
        String freeChargeRuleMode = selectMemberModelRecord.getFreeChargeRuleMode();
        Integer computeKillBaseNum = selectMemberModelRecord.getComputeKillBaseNum();
        double freeChargeRuleNum = 0;
        if(freeChargeRuleMode.equals("1")){
            freeChargeRuleNum = computeKillBaseNum;
        }else if(freeChargeRuleMode.equals("0")){
            Integer firstRank = Integer.parseInt(selectMemberModelRecord.getFirstRank());
            Integer secondRank = Integer.parseInt(selectMemberModelRecord.getSecondRank());
            freeChargeRuleNum = CalDouble.mul(CalDouble.div(computeKillBaseNum,100,10),firstRank+secondRank);
        }
        //拼接败方可免单选手ID
        String freeChargeRulePlayerList = " ";
        for (int i = 0; i < loserPlayers.size(); i++) {
            if(freeChargeRuleNum <= Integer.parseInt(loserPlayers.get(i).getKillNum())){
                freeChargeRulePlayerList += loserPlayers.get(i).getUsername()+" ";
            }
        }
        if(freeChargeRulePlayerList.trim()==""){
            freeChargeRulePlayerList = "无人";
        }
        return Result.success().data("winnerPlayers",winnerPlayers)
                .data("loserPlayers",loserPlayers)
                .data("firstRank",selectMemberModelRecord.getFirstRank())
                .data("secondRank",selectMemberModelRecord.getSecondRank())
                .data("sponsor",selectMemberModelRecord.getSponsor())
                .data("sponsorshipFunds",selectMemberModelRecord.getSponsorshipFunds())
                .data("failedTeamNeedPay",selectMemberModelRecord.getFailedTeamNeedPay())
                .data("freeChargeRuleNum",freeChargeRuleNum)
                .data("freeChargeRulePlayerList",freeChargeRulePlayerList)
                ;
    }

    @ApiOperation("删除当前指定记录")
    @Override
    public Result deleteCurrentSelectMemberModelRecord(Map params) {
        String ID = (String) params.get("ID");
        selectMemberModelRecordService.removeById(ID);
        return Result.success();
    }

    @ApiOperation("是否拥有队长权限")
    @Override
    public Result ifHasCaptainAuthority() {
        boolean ifHasCaptainAuthority = false;
        String needRole = "队长";
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(needRole)) {
                ifHasCaptainAuthority = true;
            }
        }
        return Result.success().data("ifHasCaptainAuthority",ifHasCaptainAuthority);
    }

    @ApiOperation("获取当前登录账户ID")
    @Override
    public Result getCurrentAccountID() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return Result.success().data("currentAccountID",userId);
    }

    @ApiOperation("查询4V4点将模式历史记录")
    @Override
    public Result getFourVsFourSelectMemberModelHistory(Map params) {
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        LambdaQueryWrapper<SelectMemberModelRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SelectMemberModelRecord::getIsOnGoing,"0");
        //按照日期倒序排序
        queryWrapper.orderByDesc(SelectMemberModelRecord::getStartDate);
        Page<SelectMemberModelRecord> selectMemberModelRecordPage= new Page<>(current,size);
        selectMemberModelRecordPage = selectMemberModelRecordService.page(selectMemberModelRecordPage, queryWrapper);
        List<SelectMemberModelRecord> fourVsFourSelectMemberModelHistory = selectMemberModelRecordPage.getRecords();
        //将胜方败方及各选手账单以及击杀数存入数据库
        long total = selectMemberModelRecordPage.getTotal();
        return Result.success().data("fourVsFourSelectMemberModelHistory",fourVsFourSelectMemberModelHistory).data("total",total);
    }

    @ApiOperation("在历史记录页面删除记录")
    @Override
    public Result resultPageDeleteCurrentRecord(Map params) {
        String ID = (String) params.get("ID");
        selectMemberModelRecordService.removeById(ID);
        return Result.success();
    }

    @Override
    public Result getCurrentRoundPlayers(Map params) {
        String competitionID = (String) params.get("ID");
        SelectMemberModelRecord selectMemberModelRecord = selectMemberModelRecordService.getById(competitionID);
        List<SelectMemberModelPlayer> selectMemberModelPlayerList = new ArrayList();
        SelectMemberModelPlayer selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("0");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember0());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("1");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember1());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("2");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember2());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("3");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember3());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("4");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember4());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("5");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember5());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("6");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember6());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        selectMemberModelPlayer = new SelectMemberModelPlayer();
        selectMemberModelPlayer.setTemporaryID("7");
        selectMemberModelPlayer.setUsername(selectMemberModelRecord.getMember7());
        selectMemberModelPlayerList.add(selectMemberModelPlayer);
        return Result.success().data("currentRoundPlayers",selectMemberModelPlayerList);
    }
}
