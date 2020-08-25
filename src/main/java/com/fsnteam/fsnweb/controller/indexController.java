package com.fsnteam.fsnweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class indexController {
    @RequestMapping("/FVF")
    public String fvf(Model model){
        return "FVF";
    }

    @RequestMapping("/homePage")
    public String homePage(){
        return "FsnTeam";
    }
}
