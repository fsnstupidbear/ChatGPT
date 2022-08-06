package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(type = IdType.AUTO)
    private String id;

    @TableField("CompetitionName")
    private String CompetitionName;

    private String result;

    @TableField("startDate")
    private Date startDate;

    private String type;

    @TableField("couldSignUp")
    private boolean couldSignUp;

    private String sponsor;

    private String reward;
}
