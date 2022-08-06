package com.fsnteam.fsnweb.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AllVocations {
    //基础职业
    private String value;
    private String label;
    //转职后职业列表
    private ArrayList<VocationAfterChange> vocationAfterChangeList= new ArrayList<>();
}
