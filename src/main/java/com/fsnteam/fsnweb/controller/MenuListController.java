package com.fsnteam.fsnweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fsnteam.fsnweb.entity.MenuList;
import com.fsnteam.fsnweb.service.MenuListService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-01
 */
@RestController
@RequestMapping("menuList")
public class MenuListController {

    @Autowired
    MenuListService menuListService;

    @PostMapping("getMenuList")
    public Result getMenuList(){
        List<MenuList> menuList;
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> authoritiesList = new ArrayList<String>();
        Object[] authoritiesArray = authorities.toArray();
        for (int i = 0; i < authoritiesArray.length; i++) {
            authoritiesList.add( authoritiesArray[i].toString());
        }
        //配置查询条件
        LambdaQueryWrapper<MenuList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(MenuList::getId,MenuList::getParentId
        ,MenuList::isDisabled,MenuList::getIcon,MenuList::getMenuName,MenuList::getUrl);
        queryWrapper.in(MenuList::getRequiredRole,authoritiesList);
        for(String authority:authoritiesList){
            queryWrapper.apply( authority != null,"FIND_IN_SET ('"+ authority +"', requiredRole )").or();
        }
        queryWrapper.orderBy(true,true,MenuList::getOrderBy);
        //进行菜单查询
        menuList = menuListService.list(queryWrapper);
        //子菜单的组装
        Iterator<MenuList> iterator = menuList.iterator();
        while (iterator.hasNext()){
            MenuList menuItem = iterator.next();
            if(!"".equals(menuItem.getParentId())) {
                String parentId = menuItem.getParentId();
                for (int i = 0; i < menuList.size(); i++) {
                    if(parentId.equals(menuList.get(i).getId())){
                        menuList.get(i).getChildren().add(menuItem);
                    }
                }
                iterator.remove();
            }
        }
        return Result.success().data("menuList",menuList).tip("获取菜单列表完成");
    }
}

