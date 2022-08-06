package com.fsnteam.fsnweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.entity.PointsRecord;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-26
 */
public interface PointsRecordService extends IService<PointsRecord> {

    Result insertPointsRecordById(Map params);

    Result getMyPointsChartCurrentMonthData(Map params);

}
