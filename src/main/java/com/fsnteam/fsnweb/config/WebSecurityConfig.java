package com.fsnteam.fsnweb.config;

import com.fsnteam.fsnweb.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
//指定为Spring Security配置类
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    UserDetailsService userService(){
        return new UserSecurityService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //处理未登录时返回Json为登录页面Html
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers("/").permitAll()
                .antMatchers("/vocations/**").permitAll()
                .antMatchers("/index/homePage").hasAnyRole("CAPTAIN","MEMBER")
                .antMatchers("/users/**").permitAll()
                .antMatchers("/admin/**").hasRole("CAPTAIN")
                .antMatchers("/FVF/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/").defaultSuccessUrl("/index/homePage")
                .and()
                .logout().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                //处理未登录时返回Json为登录页面Html
                .and().exceptionHandling().
                authenticationEntryPoint(authenticationEntryPoint)
                ;
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
