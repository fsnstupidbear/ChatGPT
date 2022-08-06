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
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("WhoIsUndercoverConfig")
@ApiModel(value="WhoIsUndercoverConfig对象", description="")
public class WhoIsUndercoverConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private String id;

    @TableField("undercoverNum")
    private Integer undercoverNum;

    @TableField("civilianNum")
    private Integer civilianNum;

    private String undercover;

    private String civilian;

    @TableField("blankNum")
    private Integer blankNum;
}
