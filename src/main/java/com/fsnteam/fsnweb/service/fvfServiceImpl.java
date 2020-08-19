package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import com.fsnteam.fsnweb.service.fvfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class fvfServiceImpl implements fvfService {

    @Override
    public Integer[] randNums(int reqSum) {
        Integer[] nums = new Integer[reqSum];
        boolean flag = true;
        Random rd = new Random();
        int num;
        for (int i = 0; i < reqSum; i++) {
            num = rd.nextInt(reqSum);
            //nums未赋值为空值，空值不能被比较
            //如果不为空，应该和当前随机值进行比较
            for (int j = 0; j < reqSum; j++) {
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
    public Map<String, List> divideRB(Integer[] nums) {
        List<Integer> redTeam =new ArrayList<Integer>();//红队队伍
        List<Integer> blueTeam =new ArrayList<Integer>();//蓝队队伍
        for (int i=0;i<nums.length;i++){
            if(i%2==0){
                redTeam.add(nums[i]);
            }
            if(i%2==1){
                blueTeam.add(nums[i]);
            }
        }
        //红蓝队分组信息存入Map
        Map<String,List> teaminfo=new HashMap<String,List>();
        teaminfo.put("redTeam",redTeam);
        teaminfo.put("blueTeam",blueTeam);
        return teaminfo;
    }

    @Override
    public List<PointsList> fVFdg() {
        return null;
    }


}
