package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.DvdName;
import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import com.fsnteam.fsnweb.dao.TeamTempMapper;
import com.fsnteam.fsnweb.service.fvfService;
import com.fsnteam.fsnweb.util.FVF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class fvfServiceImpl implements fvfService {

    @Autowired
    PointsMapper pointsMapper;

    @Autowired
    TeamTempMapper teamTempMapper;

    @Override
    public void updateScore(String score) {
        int[] redTeam;
        int[] blueTeam;
        switch (score){
                //红队2比0
            case "20":
                redTeam = FVF.charArrayToIntArray(teamTempMapper.queryRed().toCharArray());
                pointsMapper.updateRedScore(3,redTeam);
                break;
                //蓝队2比0
            case "02":
                blueTeam = FVF.charArrayToIntArray(teamTempMapper.queryBlue().toCharArray());
                pointsMapper.updateBlueScore(3,blueTeam);
                break;
                //红队2比1
            case "21":
                redTeam = FVF.charArrayToIntArray(teamTempMapper.queryRed().toCharArray());
                blueTeam = FVF.charArrayToIntArray(teamTempMapper.queryBlue().toCharArray());
                pointsMapper.updateRedScore(2,redTeam);
                pointsMapper.updateBlueScore(1,blueTeam);
                break;
                //蓝队2比1
            case "12":
                redTeam = FVF.charArrayToIntArray(teamTempMapper.queryRed().toCharArray());
                blueTeam = FVF.charArrayToIntArray(teamTempMapper.queryBlue().toCharArray());
                pointsMapper.updateRedScore(1,redTeam);
                pointsMapper.updateBlueScore(2,blueTeam);
                break;
        }
    }

    @Override
    public int selectCountUserName(){
        return pointsMapper.selectCountUserName();
    }

    @Override
    public void clearAllUsername() {
        pointsMapper.clearAllUsername();
    }

    @Override
    public void insertUsername(List<DvdName> nameList) {
        //多次执行Update语句，直到把所有数据全部更新进FVF表
        for (int i = 0; i < nameList.size(); i++) {
            pointsMapper.insertUsername(nameList.get(i));
        }
    }

    @Override
    public void divideGroup(int reqNum) {
        divideRB(randNums(reqNum));
    }

    @Override
    public Integer[] randNums(int reqNum) {
        Integer[] nums = new Integer[reqNum];
        boolean flag = true;
        Random rd = new Random();
        int num;
        for (int i = 0; i < reqNum; i++) {
            num = rd.nextInt(reqNum);
            //nums未赋值为空值，空值不能被比较
            //如果不为空，应该和当前随机值进行比较
            for (int j = 0; j < reqNum; j++) {
                if (nums[j] != null && num == nums[j]) {
                    i--;
                    flag = false;
                    break;
                }
            }
            if (flag != false) {
                nums[i] = num;
            }
            flag = true;
        }
        return nums;
    }

    @Override
    public void divideRB(Integer[] nums) {
        //红队队伍
        List<Integer> redTeam =new ArrayList<Integer>();
        //蓝队队伍
        List<Integer> blueTeam =new ArrayList<Integer>();
        for (int i=0;i<nums.length;i++){
            if(i%2==0){
                redTeam.add(nums[i]);
            }
            if(i%2==1){
                blueTeam.add(nums[i]);
            }
        }
        //红蓝队分组信息存入Map
        String redTeamString="";
        String blueTeamString="";
        for (int i = 0; i < redTeam.size(); i++) {
            redTeamString+=redTeam.get(i);
        }
        for (int i = 0; i < blueTeam.size(); i++) {
            blueTeamString+=blueTeam.get(i);
        }
        teamTempMapper.updateRed(redTeamString);
        teamTempMapper.updateBlue(blueTeamString);
    }
}
