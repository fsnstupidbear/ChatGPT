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
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CompetitionsSignUpInfo")
@ApiModel(value="CompetitionsSignUpInfo对象", description="")
public class CompetitionsSignUpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("competitionId")
    private String competitionId;

    @TableField("userId")
    private String userId;

    private String username;


}
