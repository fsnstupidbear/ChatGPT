package com.fsnteam.fsnweb.controller;


import com.fsnteam.fsnweb.entity.Module;
import com.fsnteam.fsnweb.service.ModuleService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-22
 */
@RestController
@RequestMapping("module")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @PostMapping("getModuleOptions")
    public Result getModuleOptions(){
        List<Module> list = moduleService.list();
        return Result.success().data("modules",list);
    }
}

