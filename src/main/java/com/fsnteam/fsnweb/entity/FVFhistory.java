package com.fsnteam.fsnweb.entity;

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
 * @since 2021-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FVFhistory")
@ApiModel(value="FVFhistory对象", description="")
public class FVFhistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String result;

    private Date date;


}
