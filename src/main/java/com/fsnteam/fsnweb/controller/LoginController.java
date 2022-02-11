package com.fsnteam.fsnweb.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 一只大笨熊
 */
@Controller
@CrossOrigin
public class LoginController {

    @RequestMapping("/")
    public String hello(Model model){
        return "login";
    }
}
