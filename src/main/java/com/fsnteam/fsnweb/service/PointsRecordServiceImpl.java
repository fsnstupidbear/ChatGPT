package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.PointsRecord;
import com.fsnteam.fsnweb.mapper.PointsRecordMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-26
 */
@Service
public class PointsRecordServiceImpl extends ServiceImpl<PointsRecordMapper, PointsRecord> implements PointsRecordService {

    @Autowired
    PointsRecordService pointsRecordService;

    @Autowired
    PointsRecordMapper pointsRecordMapper;

    @Override
    public Result insertPointsRecordById(Map params) {
        Integer userId = Integer.parseInt((String)params.get("userId"));
        String addOrMinus = (String)params.get("addOrMinus");
        Integer points = Integer.parseInt((String) params.get("points"));
        String reason = (String)params.get("reason");
        PointsRecord pointsRecord = new PointsRecord();
        pointsRecord.setUserid(userId);
        if("1".equals(addOrMinus)){

        }else if("2".equals(addOrMinus)){
            points = 0-points;
        }
        pointsRecord.setPoints(points);
        pointsRecord.setReason(reason);
        pointsRecord.setDate(new Date());
        pointsRecordService.save(pointsRecord);
        return Result.success();
    }

    @Override
    public Result getMyPointsChartCurrentMonthData(Map params) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        // 获取当前年份、月份、日期
        Calendar cale = Calendar.getInstance();
        // 获取当月第一天和最后一天
        SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取当前月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = formatTemp.format(cale.getTime());
        // 获取当前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = formatTemp.format(cale.getTime());
        //通过注解查询当前登录账号当月的积分
        List<PointsRecord> list = pointsRecordMapper.getMyPointsChartCurrentMonthData(Integer.parseInt(id), firstday, lastday);
        int currentMonthPoints = 0;
        for(PointsRecord pointsRecord : list){
            currentMonthPoints+= pointsRecord.getPoints();
        }
        List<Integer> xAxisData = new ArrayList<>();
        List<Integer> seriesData = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < dayOfMonth+1; i++) {
            xAxisData.add(i);
            //查询当前日期是否有积分，如果有返回分数，如果无返回0
            Integer points = hasCurrentDateData(i, list);
            //积累过往积分,忽略以下两步直接传回Points可以显示每天积分
            if(seriesData.size()>0) {
                Integer lastPoints = seriesData.get(seriesData.size() - 1);
                points += lastPoints;
            }
            seriesData.add(points);
        }
        //获取总积分
        QueryWrapper<PointsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(points) as sumPoints");
        queryWrapper.eq("userid",id);
        PointsRecord sum = pointsRecordService.getOne(queryWrapper);
        Integer sumPoints = sum.getSumPoints();
        return Result.success().data("xAxisData",xAxisData).data("seriesData",seriesData)
                .data("currentMonthPoints",currentMonthPoints).data("sumPoints",sumPoints);
    }

    public Integer hasCurrentDateData(Integer day,List<PointsRecord> pointsRecordList){
        Integer points = 0;
        for (int i = 0; i < pointsRecordList.size(); i++) {
            if(pointsRecordList.get(i).getDay().equals(day)){
                points = pointsRecordList.get(i).getPoints();
            }
        }
        return points;
    }
}
