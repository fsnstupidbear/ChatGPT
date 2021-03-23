package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.RoleUrlRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fsnteam.fsnweb.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2021-02-19
 */
public interface RoleUrlRelationService extends IService<RoleUrlRelation> {
    public Result selectRolesHasThisAuthority(Map params);

    public Result updateRolesHasAuthorityById(Map params);
}
