package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * DNF职业表
 * </p>
 *
 * @author StupidBear
 * @since 2021-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Vocations对象", description="DNF职业表")
public class Vocations implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("baseVocation")
    private String baseVocation;

    @TableField("vocationAfterChange")
    private String vocationAfterChange;


}
