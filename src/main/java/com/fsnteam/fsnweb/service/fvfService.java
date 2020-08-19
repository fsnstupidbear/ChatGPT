package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface fvfService {
    //随机生成8位随机数
    Integer[] randNums(int reqSum);
    Map<String, List> divideRB(Integer[] nums);
    List<PointsList> fVFdg();
}
