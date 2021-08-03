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
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("PointsRecord")
@ApiModel(value="PointsRecord对象", description="")
public class PointsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userid;

    private String reason;

    private Date date;

    private Integer points;

    private Integer day;

    private Integer sumPoints;

}
