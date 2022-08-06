package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.AllVocations;
import com.fsnteam.fsnweb.entity.Vocations;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * DNF职业表 服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-01-10
 */
public interface VocationsService extends IService<Vocations> {
    public ArrayList<AllVocations> changeVocationsEntityToList(List<Vocations> vocationsEntityList);
}
