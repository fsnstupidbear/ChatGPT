package com.fsnteam.fsnweb.entity;

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
@TableName("PlanList")
@ApiModel(value="PlanList对象", description="")
public class PlanList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String plan;


}
