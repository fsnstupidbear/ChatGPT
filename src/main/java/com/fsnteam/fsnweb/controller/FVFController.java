package com.fsnteam.fsnweb.controller;

import com.fsnteam.fsnweb.bean.DvdName;
import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import com.fsnteam.fsnweb.dao.TeamTempMapper;
import com.fsnteam.fsnweb.service.FvfService;
import com.fsnteam.fsnweb.util.FVF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
@Controller
@RequestMapping("/FVF")
public class FVFController {

    @Autowired
    FvfService fvfService;

    /**
     * 结算当前场次
     * @param request
     * @return
     */
    @RequestMapping("/checkScore")
    public String checkScore(HttpServletRequest request){
        //取得比分数据
        String score=request.getParameter("score");
        //计算积分
        fvfService.updateScore(score);
        return "redirect:/FVF/";
    }

    @RequestMapping("/")
    public String show(Model model){
        List<PointsList> pointsList = fvfService.queryAllPoints();
        //把字符串转换为字符数组再转换为整型数组
        //取得红队随机分组结果，数组存储
        int[] redTeam = FVF.charArrayToIntArray(fvfService.queryRed().toCharArray());
        //取得蓝队随机分组结果，数组存储
        int[] blueTeam = FVF.charArrayToIntArray(fvfService.queryBlue().toCharArray());
        //取得倒序排序的积分表
        List<PointsList> pointsDesc = fvfService.queryPointsDesc();
        //返回给前端
        model.addAttribute("redTeam",redTeam);
        model.addAttribute("blueTeam",blueTeam);
        model.addAttribute("pointsList",pointsList);
        model.addAttribute("pointsDesc",pointsDesc);
        return "member/FVF";
    }
    @RequestMapping("/clearAll")
    public String clear(){
        fvfService.clearPointsAndGroup();
        return "redirect:/FVF/";
    }
    /**
     * 取得页面值后先判断是否为空,判断需要几个随机数
     * if(！null||.trim!=""),存起来，全部判断结束后，一次update进FVF表
     * 生成对应随机数时，取出数据库中8个数据，查询不为空的数据并计数为reqNum
     */
    @RequestMapping("/dg")
    public String divideGroup(HttpServletRequest request){
        //分组名单数组
        String[] per=new String[8];
        List<DvdName> nameList =new ArrayList<DvdName>();
        //报名名单序号
        int id=0;
        per[0] = request.getParameter("per0");
        per[1] = request.getParameter("per1");
        per[2] = request.getParameter("per2");
        per[3] = request.getParameter("per3");
        per[4] = request.getParameter("per4");
        per[5] = request.getParameter("per5");
        per[6] = request.getParameter("per6");
        per[7] = request.getParameter("per7");

        //筛选未输入的文本框，把输入的文本框内容按顺序存入List
        for (int i = 0; i < per.length; i++) {
            if (per[i].trim()!="") {
                DvdName dvdName=new DvdName();
                dvdName.setId(id);
                dvdName.setUserName(per[i]);
                nameList.add(dvdName);
                id++;
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
        return "redirect:/FVF/";
    }
}
