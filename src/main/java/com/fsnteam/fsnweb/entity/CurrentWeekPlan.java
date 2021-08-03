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
 * @since 2021-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("currentWeekPlan")
@ApiModel(value="CurrentWeekPlan对象", description="")
public class CurrentWeekPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String day;

    private String plan;

    @TableField("planId")
    private Integer planId;


}
