package com.fsnteam.fsnweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsnteam.fsnweb.entity.RoleUrlRelation;
import com.fsnteam.fsnweb.service.RoleUrlRelationService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-02-19
 */
@CrossOrigin
@RestController
@RequestMapping("roleUrl")
public class RoleUrlRelationController {

    @Autowired
    RoleUrlRelationService roleUrlRelationService;

    @PostMapping("getAllApi")
    public Result getAllApi(@RequestBody Map params){
        String api = (String) params.get("api");
        String module = (String) params.get("module");
        int current = (int)params.get("current");
        int size = (int) params.get("size");
        LambdaQueryWrapper<RoleUrlRelation> queryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(api)) {
            queryWrapper.like(RoleUrlRelation::getUrl, api);
        }
        if(StringUtils.isNotBlank(module)) {
            queryWrapper.eq(RoleUrlRelation::getModule, module);
        }
        Page<RoleUrlRelation> page = new Page<>(current, size);
        Page<RoleUrlRelation>  urlPage = roleUrlRelationService.page(page,queryWrapper);
        long total = urlPage.getTotal();
        List<RoleUrlRelation> records = urlPage.getRecords();
        return Result.success().data("apiList",records).data("total",total);
    }

    @PostMapping("selectRolesHasThisAuthority")
    public Result rolesHasThisAuthority(@RequestBody Map params){
        return roleUrlRelationService.selectRolesHasThisAuthority(params);
    }

    @PostMapping("updateRolesHasAuthorityById")
    public Result updateRolesHasAuthorityById(@RequestBody Map params){
        return roleUrlRelationService.updateRolesHasAuthorityById(params);
    }
}

