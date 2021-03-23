package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author StupidBear
 * @since 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Competitions")
@ApiModel(value="Competitions对象", description="")
public class Competitions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("CompetitionName")
    private String CompetitionName;

    private String result;

    private Date date;


}
