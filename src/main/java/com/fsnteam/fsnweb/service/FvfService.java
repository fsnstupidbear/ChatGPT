package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.DvdName;
import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FvfService {

    List<PointsList> queryPointsDesc();

    void clearPointsAndGroup();

    //查询红队信息
    String queryRed();

    //查询蓝队信息
    String queryBlue();

    //查询全部积分表信息
    List<PointsList> queryAllPoints();

    //按照所选比分结算
    void updateScore(String score);

    //查询在表中的分组人员数量
    int selectCountUserName();

    //清空名单
    void clearAllUsername();

    //新名单加入积分表
    void insertUsername(List<DvdName> nameList);

    //分组
    void divideGroup(int reqNum);

    //随机生成8位随机数
    Integer[] randNums(int reqNum);

    void divideRB(Integer[] nums);
}
