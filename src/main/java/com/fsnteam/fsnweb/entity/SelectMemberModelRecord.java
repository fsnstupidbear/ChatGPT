package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 败方免单规则，1为固定击杀数，0为达到进行局数的百分比
 * </p>
 *
 * @author StupidBear
 * @since 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SelectMemberModelRecord")
@ApiModel(value="SelectMemberModelRecord对象", description="败方免单规则，1为固定击杀数，0为达到进行局数的百分比")
public class SelectMemberModelRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private String ID;

    private static final long serialVersionUID = 1L;

    private String sponsor;

    @TableField("sponsorshipFunds")
    private Integer sponsorshipFunds;

    @TableField("failedTeamNeedPay")
    private Integer failedTeamNeedPay;

    @TableField("freeChargeRuleMode")
    private String freeChargeRuleMode;

    @TableField("computeKillBaseNum")
    private Integer computeKillBaseNum;

    private String member1;

    private String member2;

    private String member3;

    private String member4;

    private String member5;

    private String member6;

    private String member7;

    private String member0;

    private String initiator;

    @TableField("startDate")
    private Date startDate;

    @TableField("randomCaptain")
    private String randomCaptain;

    @TableField("endDate")
    private Date endDate;

    @TableField("isOnGoing")
    private String isOnGoing;

    private String score;

    private String winner1;

    private String winner2;

    private String winner3;

    private String winner0;

    @TableField("winnerBill1")
    private String winnerBill1;

    @TableField("winnerBill2")
    private String winnerBill2;

    @TableField("winnerBill3")
    private String winnerBill3;

    @TableField("winnerBill0")
    private String winnerBill0;

    private String loser0;

    private String loser1;

    private String loser2;

    private String loser3;

    @TableField("loserBill0")
    private String loserBill0;

    @TableField("loserBill1")
    private String loserBill1;

    @TableField("loserBill2")
    private String loserBill2;

    @TableField("loserBill3")
    private String loserBill3;

    @TableField("winner0Kill")
    private String winner0Kill;

    @TableField("winner1Kill")
    private String winner1Kill;

    @TableField("winner2Kill")
    private String winner2Kill;

    @TableField("winner3Kill")
    private String winner3Kill;

    @TableField("loser0Kill")
    private String loser0Kill;

    @TableField("loser1Kill")
    private String loser1Kill;

    @TableField("loser2Kill")
    private String loser2Kill;

    @TableField("loser3Kill")
    private String loser3Kill;

    @TableField("initiatorID")
    private String initiatorID;

    private String playerLineup;

    private String firstRank;

    private String secondRank;

    private String winnerPlayers;

    private String loserPlayers;
}
