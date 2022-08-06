package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("UserRoleRelation")
@ApiModel(value="UserRoleRelation对象", description="")
public class UserRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ROLE")
    private String role;

    @TableId
    @TableField("USERID")
    private String userid;
}
