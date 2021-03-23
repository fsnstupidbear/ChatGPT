package com.fsnteam.fsnweb.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fsnteam.fsnweb.entity.RoleUrlRelation;
import com.fsnteam.fsnweb.service.RoleUrlRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 一只大笨熊
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    RoleUrlRelationService roleUrlRelationService;
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //查询具体某个接口的权限
        LambdaQueryWrapper<RoleUrlRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(RoleUrlRelation::getRole);
        queryWrapper.eq(RoleUrlRelation::getUrl,requestUrl);
        RoleUrlRelation roleUrlRelation = roleUrlRelationService.getOne(queryWrapper);
        ArrayList<String> roleList = new ArrayList<>();
        if(roleUrlRelation!=null) {
        }
        String[] roleString = new String[0];
        if(roleUrlRelation!=null) {
            roleString = roleUrlRelation.getRole().split(",");
        }
        for (int i = 0; i < roleString.length; i++) {
            if(!roleString[i].equals("")||roleString[i]==null){
                roleList.add(roleString[i]);
            }
        }
        if(roleList == null || roleList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[roleString.length];
            for(int i = 0;i<roleString.length;i++){
            attributes[i] = roleString[i];
        }
        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
