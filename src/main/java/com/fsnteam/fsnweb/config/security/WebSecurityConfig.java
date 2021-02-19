package com.fsnteam.fsnweb.config.security;

import com.fsnteam.fsnweb.service.UserSecurityService;
import com.fsnteam.fsnweb.util.login.CustomizeAuthenticationEntryPoint;
import com.fsnteam.fsnweb.util.login.CustomizeAuthenticationFailureHandler;
import com.fsnteam.fsnweb.util.login.CustomizeAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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

    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;

    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    CustomizeAbstractSecurityInterceptor securityInterceptor;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    UserDetailsService userService() {
        return new UserSecurityService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        return o;
                    }
                })
                .and()
                .formLogin()
                //登录成功处理逻辑
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                //处理未登录时返回Json为登录页面Html
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
        //增加到默认拦截链中
                http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
    }

    //加密Bcrypt
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 允许访问静态资源
     *
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
