package com.fsnteam.fsnweb.mapper;

import com.fsnteam.fsnweb.entity.PointsRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-26
 */
public interface PointsRecordMapper extends BaseMapper<PointsRecord> {

    @Select("SELECT DATE_FORMAT(date,'%d') as day,SUM(points) as points FROM PointsRecord WHERE " +
            "(userid = #{id} AND date BETWEEN #{firstDay} AND #{lastDay}) group by day")
    List<PointsRecord> getMyPointsChartCurrentMonthData(@Param("id") Integer id, @Param("firstDay") String firstDay,@Param("lastDay") String lastDay);
}
