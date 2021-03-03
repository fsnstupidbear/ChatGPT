package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.bean.AllVocations;
import com.fsnteam.fsnweb.entity.Vocations;
import com.fsnteam.fsnweb.service.VocationsService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * DNF职业表 前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-01-10
 */
@RestController
@RequestMapping("/vocations")
public class VocationsController {

    @Autowired
    VocationsService vocationsService;

    @RequestMapping("/getAllVocations")
    public Result getAllVocations(Authentication authentication) {
        //取得职业数据库
        List<Vocations> vocationsEntityList = vocationsService.list();
        //重新组合为适合树状展示的数据类型
        ArrayList<AllVocations> allVocationsList = vocationsService.changeVocationsEntityToList(vocationsEntityList);
        return Result.success().data("allVocationsList", allVocationsList).tip("获取职业列表成功");
    }
}
