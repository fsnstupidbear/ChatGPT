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
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("News")
@ApiModel(value="News对象", description="")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String id;

    private String title;

    private String content;

    private String author;

    @TableField("createdDate")
    private Date createdDate;

    @TableField("updateDate")
    private Date updateDate;

    private String type;
}
