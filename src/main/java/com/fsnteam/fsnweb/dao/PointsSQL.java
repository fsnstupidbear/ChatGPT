package com.fsnteam.fsnweb.dao;

import com.fsnteam.fsnweb.bean.DvdName;

import java.util.List;

public class PointsSQL {

    /**
     * 大帅方法
     * @param nameList
     * @return
     */
    public String insertUserName(List<DvdName> nameList) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("update FVF set USERNAME =case ID");
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append(" when "+i+" then '"+nameList.get(i).getUserName()+"'");
        }
        buffer.append("end , userid = case ID");
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append(" when "+i+" then '"+nameList.get(i).getUserid()+"'");
        }
        buffer.append(" end where ID in (");
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append(nameList.get(i).getId()+""+((i==nameList.size()-1)?"":","));
        }
        buffer.append(");");
        return buffer.toString();
    }
}
