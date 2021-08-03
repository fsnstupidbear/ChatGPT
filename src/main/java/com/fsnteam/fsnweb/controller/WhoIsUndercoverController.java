package com.fsnteam.fsnweb.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fsnteam.fsnweb.entity.WhoIsUndercoverConfig;
import com.fsnteam.fsnweb.entity.WhoIsUndercoverGamer;
import com.fsnteam.fsnweb.service.WhoIsUndercoverGamerService;
import com.fsnteam.fsnweb.service.WhoIsUndercoverService;
import com.fsnteam.fsnweb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("whoIsUndercover")
public class WhoIsUndercoverController {

    @Autowired
    WhoIsUndercoverService whoIsUndercoverService;

    @Autowired
    WhoIsUndercoverGamerService whoIsUndercoverGamerService;

    @PostMapping("configUndercover")
    public Result configWhoIsUndercover(@RequestBody Map params){
        JSONObject jsonobject = JSONObject.parseObject(JSON.toJSONString(params.get("configData")));
        WhoIsUndercoverConfig whoIsUndercover = JSON.toJavaObject(jsonobject, WhoIsUndercoverConfig.class);
        whoIsUndercover.setId("1");
        whoIsUndercoverService.updateById(whoIsUndercover);
        return Result.success().tip("配置完成");
    }

    @PostMapping("isFullOfLength")
    public Result isFullOfLength(){
        //当前已进入房间的玩家数量
        int gamerCount = whoIsUndercoverGamerService.count();
        List<WhoIsUndercoverConfig> list = whoIsUndercoverService.list();
        WhoIsUndercoverConfig whoIsUndercoverConfig = new WhoIsUndercoverConfig();
        Integer blankNum;
        Integer civilianNum;
        Integer undercoverNum;
        int allIdentifyCount = 0;
        if(list.size()==1) {
            whoIsUndercoverConfig = list.get(0);
            blankNum = whoIsUndercoverConfig.getBlankNum();
            civilianNum = whoIsUndercoverConfig.getCivilianNum();
            undercoverNum = whoIsUndercoverConfig.getUndercoverNum();
            allIdentifyCount = blankNum + civilianNum + undercoverNum;
        }
        boolean isFullOfLength = (gamerCount>=allIdentifyCount);
        return Result.success().data("isFullOfLength",isFullOfLength);
    }

    @PostMapping("controllerGetCurrentGameInfo")
    @ApiOperation("配置者获取当前玩家信息")
    public Result controllerGetCurrentGameInfo(){
        LambdaQueryWrapper<WhoIsUndercoverGamer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(WhoIsUndercoverGamer::getNumber);
        List<WhoIsUndercoverGamer> controllerGetCurrentGameInfo = whoIsUndercoverGamerService.list(queryWrapper);
        return Result.success().data("controllerGetCurrentGameInfo",controllerGetCurrentGameInfo);
    }

    @PostMapping("out")
    @ApiOperation("踢出淘汰的玩家")
    public Result out(@RequestBody Map params){
        Integer id = (Integer) params.get("id");
        whoIsUndercoverGamerService.removeById(id);
        return Result.success();
    }
}
