package com.fsnteam.fsnweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fsnteam.fsnweb.bean.DvdName;
import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.entity.FvfRecord;
import com.fsnteam.fsnweb.entity.PointsRecord;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.mapper.PointsRecordMapper;
import com.fsnteam.fsnweb.service.FvfRecordService;
import com.fsnteam.fsnweb.service.FvfService;
import com.fsnteam.fsnweb.service.PointsRecordService;
import com.fsnteam.fsnweb.service.UsersService;
import com.fsnteam.fsnweb.util.FVF;
import com.fsnteam.fsnweb.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 打开页面时：/FVF/进入
 * 从数据库中取出当前参赛选手名单 √
 * 展示在1-8文本框中 √
 * 在积分表展示积分 √
 *
 * 从数据库导出分组编号
 * {
 *     select
 * }
 * 根据编号将对应选手的ID显示在红蓝队文本框内
 * {
 *     1.用split方法将数字取出到数组中
 *     2.把编号存入map
 * }
 * 从数据库中取出当前积分情况，把积分表展示在积分表文本框中 √
 *
 * 分组：
 * 调用FVFController的dg进行分组，把分组信息存入数据库的红蓝队编号中
 * 并从前端页面获取队员ID，把全部队员ID按顺序保存至数据库中
 *
 * 确认提交比分：
 * 从后端页面获取选手当前积分后根据当前比分情况
 *
 * 先取得红蓝队当前分组的编号，对红蓝队每人积分操作
 * if（2：0） 红队+3 else if（2：1）红队+2 蓝队+1 if（1：2）红队+1 蓝队+2 if（0：2）蓝队+3
 *
 * 计算并存入Map返回给后端，存入数据库

 */
@RestController
@RequestMapping("/FVF")
public class FVFController {

    @Autowired
    FvfService fvfService;

    @Autowired
    UsersService usersService;

    @Autowired
    PointsRecordMapper pointsRecordMapper;

    @Autowired
    FvfRecordService fvfRecordService;

    @Autowired
    PointsRecordService pointsRecordService;

    /**
     * 结算当前场次
     * @param
     * @return
     */
    @PostMapping("/checkScore")
    @ApiOperation("获取比分数据，更新参赛选手积分")
    public Result checkScore(@RequestBody Map params){
        //取得比分数据
        String score= (String) params.get("score");
        //计算积分
        fvfService.updateScore(score);
        return Result.success().tip("积分结算完成");
    }

    @PostMapping("/")
    @ApiOperation(value = "查询分组情况")
    public Result show(){
        List<PointsList> pointsList = fvfService.queryAllPoints();
        //把字符串转换为字符数组再转换为整型数组
        //取得红队随机分组结果，数组存储
        int[] redTeam = FVF.charArrayToIntArray(fvfService.queryRed().toCharArray());
        //取得蓝队随机分组结果，数组存储
        int[] blueTeam = FVF.charArrayToIntArray(fvfService.queryBlue().toCharArray());
        //取得倒序排序的积分表
        List<PointsList> pointsDesc = fvfService.queryPointsDesc();
        //        1111111111111111计算账单11111111111111111111111
        List<Double> checkMoney= fvfService.checkMoney(pointsDesc);
        //组装积分和账单
        pointsDesc = fvfService.mergePointsAndAccount(pointsDesc, checkMoney);
//        //        1111111111111111计算账单11111111111111111111111
        return Result.success()
                .data("redTeam",redTeam)
                .data("blueTeam",blueTeam)
                .data("pointsDesc",pointsDesc)
                .data("pointsList",pointsList);
    }
    @PostMapping("/clearAll")
    @ApiOperation("清空分组器")
    public Result clear(){
        //把当前积分全部存入积分记录
        LambdaQueryWrapper<FvfRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(FvfRecord::getUsername,"null");
        List<FvfRecord> fvfPointsList = fvfRecordService.list(queryWrapper);
        List<PointsRecord> pointsRecordList = new ArrayList<>();
        for(FvfRecord fvfRecord : fvfPointsList){
            PointsRecord pointsRecord = new PointsRecord();
            pointsRecord.setPoints(Integer.parseInt(fvfRecord.getPoints()));
            pointsRecord.setUserid(fvfRecord.getUserid());
            pointsRecord.setReason("Fsn队内擂台");
            pointsRecord.setDate(new Date());
            pointsRecordList.add(pointsRecord);
        }
        pointsRecordService.saveBatch(pointsRecordList);
        //清空分组器
        fvfService.clearPointsAndGroup();
        return Result.success().tip("分组数据已初始化");
    }
    /**
     * 取得页面值后先判断是否为空,判断需要几个随机数
     * if(！null||.trim!=""),存起来，全部判断结束后，一次update进FVF表
     * 生成对应随机数时，取出数据库中8个数据，查询不为空的数据并计数为reqNum
     * @return
     */
    @PostMapping("/dg")
    @ApiOperation(value = "分组")
    public Result divideGroup(@RequestBody Map params){
        //获取全部队员列表
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Users::getId,Users::getUsername);
        List<Users> teamMember = usersService.list(queryWrapper);
        //
        List<DvdName> nameList =new ArrayList<>();
        //报名名单序号
        List<PointsList> pointsList = JSONArray.parseArray((String) params.get("persons"), PointsList.class);
        //筛选未输入的文本框，把输入的文本框内容按顺序存入List
        for (int i = 0; i < pointsList.size(); i++) {
            if(!(pointsList.get(i).getUsername() == null || pointsList.get(i).getUsername().trim().length() == 0)) {
                //根据前端传回的username获取队员ID
                Integer userid = selectIdByUsername(teamMember,pointsList.get(i).getUsername());
                if(userid==null){
                    return Result.error().tip("传输数据有误！");
                }
                DvdName dvdName=new DvdName();
                dvdName.setId(i);
                dvdName.setUserid(userid);
                dvdName.setUserName(pointsList.get(i).getUsername());
                nameList.add(dvdName);
            }
        }
        //清空名单
        fvfService.clearAllUsername();
        //把新名单加入积分表
        fvfService.insertUsername(nameList);
        //随机分组需求数量参数
        int reqNum=fvfService.selectCountUserName();
        //生成对应数量的随机数
        fvfService.divideGroup(reqNum);
        return Result.success().tip("分组已完成");
    }

    @PostMapping("getTeamMember")
    public Result getTeamMember(){
        return fvfService.getTeamMember();
    }


    //根据username查询ID
    public Integer selectIdByUsername(List<Users> teamMember,String username){
        for(Users user : teamMember){
            if(user.getUsername().equals(username)){
                return Integer.parseInt(user.getId());
            }
        }
        return null;
    }
}
