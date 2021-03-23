package com.fsnteam.fsnweb.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.entity.FVFhistory;
import com.fsnteam.fsnweb.mapper.FVFhistoryMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-19
 */
@Service
public class FVFhistoryServiceImpl extends ServiceImpl<FVFhistoryMapper, FVFhistory> implements FVFhistoryService {

    @Autowired
    FVFhistoryService fvFhistoryService;

    @Override
    public Result getAllFVFHistory(Map params) {
        int current = (int) params.get("current");
        int size = (int) params.get("size");
        LambdaQueryWrapper<FVFhistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(FVFhistory::getId,FVFhistory::getDate,FVFhistory::getResult);
        Page<FVFhistory> page = new Page<>(current,size);
        Page<FVFhistory> fvFhistorypage = fvFhistoryService.page(page,queryWrapper);
        long total = fvFhistorypage.getTotal();
        List<FVFhistory> records = fvFhistorypage.getRecords();
        return Result.success().data("total",total).data("fvfHistory",records);
    }

    @Override
    public void saveCurrentRound(Map params) {
        String s = JSONArray.toJSONString(params.get("pointsList"));
        List<PointsList> pointsList = JSONArray.parseArray(s, PointsList.class);
        String result = "";
        for (int i = 0; i < pointsList.size(); i++) {
            result = result + pointsList.get(i).getUsername()+":"+pointsList.get(i).getCheckMoney()+" ";
        }
        FVFhistory fvFhistory = new FVFhistory();
        fvFhistory.setDate(new Date());
        fvFhistory.setResult(result);
        fvFhistoryService.save(fvFhistory);
    }
}
