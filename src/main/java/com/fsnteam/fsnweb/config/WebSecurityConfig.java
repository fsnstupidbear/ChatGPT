package com.fsnteam.fsnweb.config;

import com.fsnteam.fsnweb.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: WebSecurityConfig
 * Author:   longzhonghua
 * Date:     2019/5/7 12:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Configuration
@EnableWebSecurity//指定为Spring Security配置类
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService userService(){
        return new UserSecurityService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/FVF/","/index/FVF","/index/homePage").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/").defaultSuccessUrl("/index/homePage")
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
    }

    //加密Bcrypt
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 允许访问静态资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web.ignoring().antMatchers("/**/**.*");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}
