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

import java.util.Collection;
import java.util.List;

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
        queryWrapper.select(RoleUrlRelation::getRole,RoleUrlRelation::getUrl);
        queryWrapper.like(RoleUrlRelation::getUrl,requestUrl);
        List<RoleUrlRelation> permissionList =  roleUrlRelationService.list(queryWrapper);
        if(permissionList == null || permissionList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissionList.size()];
            for(int i = 0;i<permissionList.size();i++){
            attributes[i] = permissionList.get(i).getRole();
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
