package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.VocationAfterChange;
import com.fsnteam.fsnweb.bean.AllVocations;
import com.fsnteam.fsnweb.entity.Vocations;
import com.fsnteam.fsnweb.mapper.VocationsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * DNF职业表 服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-01-10
 */
@Service
public class VocationsServiceImpl extends ServiceImpl<VocationsMapper, Vocations> implements VocationsService {

    @Override
    public ArrayList<AllVocations> changeVocationsEntityToList(List<Vocations> vocationsEntityList) {
        ArrayList<AllVocations> allVocationsList = new ArrayList<AllVocations>();


        for (int i = 0; i < vocationsEntityList.size(); i++) {
            AllVocations allVocations = new AllVocations();
            //取得单行数据，即每一个基础职业数据行
            Vocations vocations = vocationsEntityList.get(i);
            //取得基础职业
            String baseVocation =vocations.getBaseVocation();
            allVocations.setValue(baseVocation);
            allVocations.setLabel(baseVocation);
            //取得转职字符串
            String vocationAfterChangeString = vocations.getVocationAfterChange();
            //转职字符串转换为字符串数组
            String[] vocationAfterChangeArray =  vocationAfterChangeString.split(",");
            for (int j = 0; j < vocationAfterChangeArray.length; j++) {
                VocationAfterChange vocationAfterChange = new VocationAfterChange();
                vocationAfterChange.setLabel(vocationAfterChangeArray[j]);
                vocationAfterChange.setValue(vocationAfterChangeArray[j]);
                allVocations.getVocationAfterChangeList().add(vocationAfterChange);
            }
            allVocationsList.add(allVocations);
        }
        return allVocationsList;
    }
}
