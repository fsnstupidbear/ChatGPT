package com.fsnteam.fsnweb.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fsnteam.fsnweb.entity.RoleUrlRelation;
import com.fsnteam.fsnweb.mapper.RoleUrlRelationMapper;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2021-02-19
 */
@Service
public class RoleUrlRelationServiceImpl extends ServiceImpl<RoleUrlRelationMapper, RoleUrlRelation> implements RoleUrlRelationService {

    @Autowired
    RoleUrlRelationService roleUrlRelationService;

    @Override
    public Result selectRolesHasThisAuthority(Map params) {
        String url = JSONObject.toJSONString(params.get("url"));
        String handledUrl = url.replaceAll("\"", "");
        LambdaQueryWrapper<RoleUrlRelation> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(RoleUrlRelation::getUrl, handledUrl);
        RoleUrlRelation one = roleUrlRelationService.getOne(queryWrapper);
        String roleString = new String();
        String[] rolesHasThisAuthority = new String[0];
        if(one!=null) {
            roleString = one.getRole();
            rolesHasThisAuthority = roleString.split(",");
        }
        return Result.success().data("rolesHasThisAuthority",rolesHasThisAuthority);
    }

    @Override
    public Result updateRolesHasAuthorityById(Map params) {
        String url = JSONObject.toJSONString(params.get("url")).replaceAll("\"", "");
        ArrayList roles = (ArrayList) params.get("roles");
        String rolesString="";
        for (int i = 0; i < roles.size(); i++) {
            rolesString = rolesString+roles.get(i);
            if(i+1<roles.size()){
                rolesString+=",";
            }
        }
        RoleUrlRelation roleUrlRelation = new RoleUrlRelation();
        roleUrlRelation.setUrl(url);
        roleUrlRelation.setRole(rolesString);
        roleUrlRelationService.updateById(roleUrlRelation);
        return Result.success().tip("更新角色完成！");
    }
}
