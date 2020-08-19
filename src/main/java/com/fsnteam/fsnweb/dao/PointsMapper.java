package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.PointsList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PointsMapper {
    @Select("select id,username,points from FVF")
    List<PointsList> queryAll();
}
