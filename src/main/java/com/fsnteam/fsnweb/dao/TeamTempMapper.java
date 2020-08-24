package com.fsnteam.fsnweb.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamTempMapper {
    //查询红队随机分组编号
    @Select("select identifier from teamTemp where team='red'")
    String queryRed();

    //查询蓝队随机分组编号
    @Select("select identifier from teamTemp where team='blue'")
    String queryBlue();

    //更新红队随机分组
    @Update("update teamTemp set identifier=#{redteam} where team='red'")
    void updateRed(String redTeam);

    //更新蓝队随机分组
    @Update("update teamTemp set identifier=#{blueteam} where team='blue'")
    void updateBlue(String blueTeam);

    //删除全部暂存队伍信息，未开放
    @Delete("delete * from teamTemp")
    void deleteAll();
    }
