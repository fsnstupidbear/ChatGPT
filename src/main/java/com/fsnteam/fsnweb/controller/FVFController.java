package com.fsnteam.fsnweb.controller;

import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import com.fsnteam.fsnweb.service.fvfService;
import com.sun.deploy.nativesandbox.comm.Request;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
/**
 * 打开页面时：
 * 从数据库中取出当前参赛选手名单，展示在1-8文本框中
 * 从数据库导出分组编号，根据编号将对应选手的ID显示在红蓝队文本框内
 * 从数据库中取出当前积分情况，把积分表展示在积分表文本框中
 *
 * 分组：
 * 调用FVFController的dg进行分组，把分组信息存入数据库的红蓝队编号中
 * 并从前端页面获取队员ID，把全部队员ID按顺序保存至数据库中
 *
 * 结算：
 * 从前端页面获取选手ID后并存入数据库
 * 从后端页面获取选手当前积分后根据当前比分情况
 *
 * 先取得红蓝队当前分组的编号，对红蓝队每人积分操作
 * if（2：0） 红队+3 else if（2：1）红队+2 蓝队+1 if（1：2）红队+1 蓝队+2 if（0：2）蓝队+3
 *
 * 计算并存入Map返回给后端，存入数据库

 */
@RequestMapping("/FVF")
public class FVFController {
    @Autowired
    fvfService fvfService;
    @Autowired
    PointsMapper pointsMapper;

    @RequestMapping("/")
    public String show(Model model){
        System.out.println("1111111");
        List<PointsList> pointsList = pointsMapper.queryAll();
        model.addAttribute("pointsList",pointsList);
        System.out.println(pointsList);
        return "FVF";
    }

    @RequestMapping("/dg")
    public String divideGroup(HttpServletRequest request){
        String per0 = request.getParameter("per0");
        String per1 = request.getParameter("per1");
        String per2 = request.getParameter("per2");
        String per3 = request.getParameter("per3");
        String per4 = request.getParameter("per4");
        String per5 = request.getParameter("per5");
        String per6 = request.getParameter("per6");
        String per7 = request.getParameter("per7");
        System.out.println("123");
        Map accountName=new HashMap<String,String>();
        Map accountpoints=new HashMap<String,Integer>();
        int reqSum=8;
        Integer[] nums = fvfService.randNums(reqSum);
        Map<String, List> rbGroup = fvfService.divideRB(nums);
//        return rbGroup;
        return "FVF";
    }
}
