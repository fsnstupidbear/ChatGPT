package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author StupidBear
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("RoleUrlRelation")
@ApiModel(value="RoleUrlRelation对象", description="")
public class RoleUrlRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("Role")
    private String Role;

    @TableField("Url")
    private String Url;


}
