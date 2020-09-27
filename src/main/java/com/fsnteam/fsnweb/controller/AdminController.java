package com.fsnteam.fsnweb.controller;

import com.fsnteam.fsnweb.bean.User;
import com.fsnteam.fsnweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String show(){
        return "captain/insertMember";
    }

    /**
     * 添加队员并自动赋权限为member
     * @param user
     * @return
     */
    @RequestMapping("insert")
    public String insertMember(User user){
        userService.insertMember(user);
        return "captain/insertMember";
    }
}
