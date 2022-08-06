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
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("CompetitionClassify")
@ApiModel(value="CompetitionClassify对象", description="")
public class CompetitionClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private int id;

    @TableField("CompetitionClassify")
    private String CompetitionClassify;


}
