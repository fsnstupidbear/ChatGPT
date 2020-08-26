package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.DvdName;
import com.fsnteam.fsnweb.bean.PointsList;
import org.apache.ibatis.annotations.*;
import org.w3c.dom.NameList;

import java.util.List;

@Mapper
public interface PointsMapper {

    //查询倒序积分表
    @Select("select id,username,points from FVF order by points desc")
    List<PointsList> queryPointsDesc();

    //查询积分表全部信息
    @Select("select id,username,points from FVF")
    List<PointsList> queryAll();

    //删除全部积分表信息,未实现接口
    @Delete("delete * from FVF")
    void deleteAll();

    //清空全部username
    @Update("UPDATE FVF SET USERNAME = null")
    void clearAllUsername();

    @Update("update FVF set points = 0")
    void clearAllPoints();

    //把新的分组名单写入积分表
    @UpdateProvider(type = PointsSQL.class,method = "insertUserName")
    void insertUsername(@Param("nameList")List<DvdName> nameList);

    @Select("select count(userName) from FVF")
    int selectCountUserName();

    //更新红队比分信息
    @Update("<script>" +
            "        update FVF set points = points+#{score} where ID in " +
            "        <foreach collection='redTeam' open='(' item='item' separator=',' close=')'> #{item}</foreach>" +
            "        </script>")
    void updateRedScore(int score,int[] redTeam);

    //更新蓝队比分信息
    @Update("<script>" +
            "        update FVF set points = points+#{score} where ID in " +
            "        <foreach collection='blueTeam' open='(' item='item' separator=',' close=')'> #{item}</foreach>" +
            "        </script>")
    void updateBlueScore(@Param("score") int score,@Param("blueTeam") int [] blueTeam);
}
