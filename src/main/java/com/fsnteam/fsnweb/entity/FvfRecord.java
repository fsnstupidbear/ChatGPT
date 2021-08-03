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
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FVF")
@ApiModel(value="FvfRecord对象", description="")
public class FvfRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("USERNAME")
    private String username;

    @TableField("POINTS")
    private String points;

    private Integer userid;


}
