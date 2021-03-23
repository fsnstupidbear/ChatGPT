package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("WhoIsUndercoverGamer")
@ApiModel(value="WhoIsUndercoverGamer对象", description="")
public class WhoIsUndercoverGamer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @TableField("userID")
    private String userID;

    private String username;

    private String identify;

    private Integer number;

    private String word;
}
