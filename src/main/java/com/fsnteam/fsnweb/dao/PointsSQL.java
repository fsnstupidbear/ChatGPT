package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.DvdName;

import java.util.List;

public class PointsSQL {
    public String insertUserName(DvdName dvdName){
        return "update FVF set username='"+dvdName.getUserName()+"'where id='"+dvdName.getId()+"'";
    }
}
