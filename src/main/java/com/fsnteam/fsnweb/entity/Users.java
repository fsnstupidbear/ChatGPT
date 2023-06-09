package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.Predicate;

import java.io.Serializable;
import java.util.Date;

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
public class Users implements Serializable, Predicate {

    @TableField("ID")
    @TableId(type = IdType.AUTO)
    private String id;

    @TableField("PASSWORD")
    private String password;

    @TableField("USERNAME")
    private String username;

    @TableField("ISENABLED")
    private String isEnabled;

    @TableField("VOCATION")
    private String vocation;

    @TableField("DEPARTMENT")
    private String department;

    @TableField("QQ")
    private String QQNumber;

    @TableField("JOIN_DATE")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date joinDate;

    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    @Override
    public boolean evaluate(Object o) {
        return false;
    }
}
