package com.fsnteam.fsnweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsnteam.fsnweb.dao.UserMapper;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.entity.WhoIsUndercoverConfig;
import com.fsnteam.fsnweb.entity.WhoIsUndercoverGamer;
import com.fsnteam.fsnweb.mapper.WhoIsUndercoverGamerMapper;
import com.fsnteam.fsnweb.service.FvfService;
import com.fsnteam.fsnweb.service.UsersService;
import com.fsnteam.fsnweb.service.WhoIsUndercoverGamerService;
import com.fsnteam.fsnweb.service.WhoIsUndercoverService;
import com.fsnteam.fsnweb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-08
 */
@CrossOrigin
@RestController
@RequestMapping("whoIsUndercoverGamer")
public class WhoIsUndercoverGamerController {
    @Autowired
    WhoIsUndercoverGamerService whoIsUndercoverGamerService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UsersService usersService;

    @Autowired
    WhoIsUndercoverService whoIsUndercoverService;

    @Autowired
    FvfService fvfService;

    @Autowired
    WhoIsUndercoverGamerMapper whoIsUndercoverGamerMapper;

    @PostMapping("isInCurrentRound")
    @ApiOperation("判断当前用户是否已在此局游戏内")
    public Result isInCurrentRound(){
        boolean isInCurrentRound;
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        LambdaQueryWrapper<WhoIsUndercoverGamer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WhoIsUndercoverGamer::getUserID,id);
        List<WhoIsUndercoverGamer> list = whoIsUndercoverGamerService.list(queryWrapper);
        if(list.size()>=1){
            isInCurrentRound = true;
        }else {
            isInCurrentRound = false;
        }
        return Result.success().data("isInCurrentRound",isInCurrentRound);
    }

    @PostMapping("joinCurrentGame")
    @ApiOperation("加入此局游戏")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Result joinCurrentGame(){
        int count = whoIsUndercoverService.count();
        if(count==0){
            return Result.error().tip("当前游戏未开始");
        }
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.getById(id);
        int gamerCount = whoIsUndercoverGamerService.count();
        //获取设置的玩家数量
        WhoIsUndercoverGamer whoIsUndercoverGamer = new WhoIsUndercoverGamer();
        whoIsUndercoverGamer.setId(gamerCount+1);
        whoIsUndercoverGamer.setUserID(user.getId());
        whoIsUndercoverGamer.setUsername(user.getUsername());
        whoIsUndercoverGamerService.save(whoIsUndercoverGamer);

        //获取设置玩家数量
        List<WhoIsUndercoverConfig> list = whoIsUndercoverService.list();
        WhoIsUndercoverConfig whoIsUndercoverConfig = list.get(0);
        String civilian = whoIsUndercoverConfig.getCivilian();
        String undercover = whoIsUndercoverConfig.getUndercover();
        Integer blankNum = whoIsUndercoverConfig.getBlankNum();
        Integer civilianNum = whoIsUndercoverConfig.getCivilianNum();
        Integer undercoverNum = whoIsUndercoverConfig.getUndercoverNum();
        int allIdentifyCount = blankNum+civilianNum+undercoverNum;
        //随机分配身份
        if(gamerCount+1>=allIdentifyCount){
            List<String> identifyArray = new ArrayList<>();
            for (int i = 0; i < blankNum; i++) {
                identifyArray.add("白板");
            }
            for (int i = 0; i < civilianNum; i++) {
                identifyArray.add("平民");
            }
            for (int i = 0; i < undercoverNum; i++) {
                identifyArray.add("卧底");
            }
            //打乱身份顺序
            Collections.shuffle(identifyArray);
            //随机生成序号
            Integer[] randNums = fvfService.randNums(allIdentifyCount);
            //插入随机顺序和随机身份数据
            for (int i = 1; i < allIdentifyCount+1; i++) {
                WhoIsUndercoverGamer whoIsUndercoverGamer1 = new WhoIsUndercoverGamer();
                whoIsUndercoverGamer1.setId(i);
                whoIsUndercoverGamer1.setIdentify(identifyArray.get(i-1));
                //设置用户词
                if(identifyArray.get(i-1).equals("卧底")){
                    whoIsUndercoverGamer1.setWord(undercover);
                }else if(identifyArray.get(i-1).equals("平民")){
                    whoIsUndercoverGamer1.setWord(civilian);
                }else {
                    whoIsUndercoverGamer1.setWord("白板");
                }
                whoIsUndercoverGamer1.setNumber(randNums[i-1]+1);
                whoIsUndercoverGamerService.updateById(whoIsUndercoverGamer1);
            }
        }

        return Result.success();
    }

    @PostMapping("playerGetCurrentGameInfo")
    @ApiOperation("获取玩家身份应获得的信息")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Result playerGetCurrentGameInfo(@RequestBody Map params){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        LambdaQueryWrapper<WhoIsUndercoverGamer> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(WhoIsUndercoverGamer::getUserID,id);
        queryWrapper1.select(WhoIsUndercoverGamer::getWord);
        WhoIsUndercoverGamer whoIsUndercoverGamer = whoIsUndercoverGamerService.getOne(queryWrapper1);
        String word = "";
        if(whoIsUndercoverGamer!=null){
            word = whoIsUndercoverGamer.getWord();
        }
        int current = (int)params.get("current");
        int size = (int)params.get("size");
        LambdaQueryWrapper<WhoIsUndercoverGamer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WhoIsUndercoverGamer::getNumber,WhoIsUndercoverGamer::getUsername);
        queryWrapper.orderByAsc(WhoIsUndercoverGamer::getNumber);
        Page<WhoIsUndercoverGamer> page = new Page<>(current,size);
        Page<WhoIsUndercoverGamer> playerGetCurrentGameInfoPage = whoIsUndercoverGamerService.page(page,queryWrapper);
        long total = playerGetCurrentGameInfoPage.getTotal();
        List<WhoIsUndercoverGamer> playerInfoList = playerGetCurrentGameInfoPage.getRecords();

        return Result.success().data("playerGetCurrentGameInfo",playerInfoList).data("total",total)
                .data("word",word);
    }

    @PostMapping("clearAllGamerInfo")
    @ApiOperation("清除全部用户信息")
    public Result clearAllGamerInfo(){
        whoIsUndercoverGamerMapper.deleteGamerInfo();
        WhoIsUndercoverConfig whoIsUndercoverConfig = new WhoIsUndercoverConfig();
        whoIsUndercoverConfig.setId("1");
        whoIsUndercoverConfig.setBlankNum(0);
        whoIsUndercoverConfig.setCivilianNum(0);
        whoIsUndercoverConfig.setUndercoverNum(0);
        whoIsUndercoverService.updateById(whoIsUndercoverConfig);
        return Result.success();
    }
}

