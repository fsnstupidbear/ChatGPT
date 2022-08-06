package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("MenuList")
@ApiModel(value="MenuList对象", description="")
public class MenuList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

    @TableField("id")
    private String id;

    @TableField("menuName")
    private String menuName;

    @TableField("icon")
    private String icon;

    @TableField("disabled")
    private boolean disabled;

    @TableField("parentId")
    private String parentId;

    @TableField("requiredRole")
    private String requiredRole;

    private List<MenuList> children = new ArrayList<>();

    @TableField("orderBy")
    private String orderBy;
}
