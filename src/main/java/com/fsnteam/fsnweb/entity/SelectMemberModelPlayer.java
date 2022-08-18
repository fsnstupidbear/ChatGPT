package com.fsnteam.fsnweb.entity;

import lombok.Data;

@Data
public class SelectMemberModelPlayer {

    private String temporaryID;
    private String username;
    private String bill;
    private String killNum;
    private boolean isNeedToPay = true;
    private double currentLoserPlayerCoefficient;
}
