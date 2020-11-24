package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author StupidBear
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("USERS")
@ApiModel(value="Users对象", description="")
public class Users implements Serializable {

    @TableField("ID")
    private String id;

    @TableField("PASSWORD")
    private String password;

    @TableField("USERNAME")
    private String username;

    @TableField("ISENABLED")
    private String isenabled;

    @TableField("VOCATION")
    private String vocation;


}
