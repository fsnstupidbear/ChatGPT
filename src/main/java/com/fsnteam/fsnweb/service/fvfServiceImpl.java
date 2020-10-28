package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.bean.DvdName;
import com.fsnteam.fsnweb.bean.PointsList;
import com.fsnteam.fsnweb.dao.PointsMapper;
import com.fsnteam.fsnweb.dao.TeamTempMapper;
import com.fsnteam.fsnweb.util.CalDouble;
import com.fsnteam.fsnweb.util.FVF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class fvfServiceImpl implements FvfService {

    @Autowired
    PointsMapper pointsMapper;

    @Autowired
    TeamTempMapper teamTempMapper;

    @Override
    public List<PointsList> queryPointsDesc() {
        return pointsMapper.queryPointsDesc();
    }

    //清空积分表和分组
    @Override
    public void clearPointsAndGroup() {
        pointsMapper.clearAllPoints();
        teamTempMapper.clearAllInentifier();
    }

    @Override
    public String queryRed() {
        return teamTempMapper.queryRed();
    }

    @Override
    public String queryBlue() {
        return teamTempMapper.queryBlue();
    }

    //查询积分表全部信息
    @Override
    public List<PointsList> queryAllPoints() {
        return pointsMapper.queryAll();
    }

    @Override
    public void updateScore(String score) {
        int[] redTeam;
        int[] blueTeam;
        switch (score) {
            //红队2比0
            case "20":
                redTeam = FVF.charArrayToIntArray(teamTempMapper.queryRed().toCharArray());
                if (redTeam != null) {
                    pointsMapper.updateRedScore(3, redTeam);
                }
                break;
            //蓝队2比0
            case "02":

                blueTeam = FVF.charArrayToIntArray(teamTempMapper.queryBlue().toCharArray());
                if (blueTeam != null) {
                    pointsMapper.updateBlueScore(3, blueTeam);
                }
                break;
            //红队2比1
            case "21":
                redTeam = FVF.charArrayToIntArray(teamTempMapper.queryRed().toCharArray());
                blueTeam = FVF.charArrayToIntArray(teamTempMapper.queryBlue().toCharArray());
                if (redTeam != null) {
                    pointsMapper.updateRedScore(2, redTeam);
                }
                if (blueTeam != null) {
                    pointsMapper.updateBlueScore(1, blueTeam);
                }
                break;
            //蓝队2比1
            case "12":
                redTeam = FVF.charArrayToIntArray(teamTempMapper.queryRed().toCharArray());
                blueTeam = FVF.charArrayToIntArray(teamTempMapper.queryBlue().toCharArray());
                if (redTeam != null) {
                    pointsMapper.updateRedScore(1, redTeam);
                }
                if (blueTeam != null) {
                    pointsMapper.updateBlueScore(2, blueTeam);
                }
                break;
        }
    }

    @Override
    public int selectCountUserName() {
        return pointsMapper.selectCountUserName();
    }

    @Override
    public void clearAllUsername() {
        pointsMapper.clearAllUsername();
    }

    @Override
    public void insertUsername(List<DvdName> nameList) {
        //多次执行Update语句，直到把所有数据全部更新进FVF表
        pointsMapper.insertUsername(nameList);
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
        List<Integer> redTeam = new ArrayList<Integer>();
        //蓝队队伍
        List<Integer> blueTeam = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 0) {
                redTeam.add(nums[i]);
            }
            if (i % 2 == 1) {
                blueTeam.add(nums[i]);
            }
        }
        //红蓝队分组信息存入Map
        String redTeamString = "";
        String blueTeamString = "";
        for (int i = 0; i < redTeam.size(); i++) {
            redTeamString += redTeam.get(i);
        }
        for (int i = 0; i < blueTeam.size(); i++) {
            blueTeamString += blueTeam.get(i);
        }
        teamTempMapper.updateRed(redTeamString);
        teamTempMapper.updateBlue(blueTeamString);
    }

    @Override
    //计算当前积分情况下的结账账单信息
    public List<Double> checkMoney(List<PointsList> pointsDesc) {
        //赞助
        double sponsor = 25;
        //报名费
        double RegistrationFee = 3;
        //1234名的奖金池瓜分比例
        double[] divideProportion = {0.40, 0.30, 0.20, 0.10, 0, 0, 0, 0};
        int countUsername = selectCountUserName();
        //
        double rewardPool = sponsor + RegistrationFee * countUsername;
        //结算账单
        List<Double> checkMoney = new ArrayList<Double>();
        int[] points = new int[countUsername];

        //11111111111把list转换为数组111111111111111111
        for (int i = 0; i < countUsername; i++) {
            points[i] = pointsDesc.get(i).getPoints();
        }
        //11111111111把list转换为数组111111111111111111

        /**
         *         找出积分最高的，判断有几人与他同分存入变量tempSum，移动下标index+=tempSum
         *         把当前积分分得奖金存入checkMoney列表中，for(i=0;i<tempSum;i++)
         *         当index下标小于参赛人数时循环
         */
        //当前游标，上一个积分相同的游标，当前积分站有奖金池百分比
        int index = 0, lastIndex = 0;
        double reward = 0;
        while (index < countUsername) {
            int tempSum = 0;
            double sumDivideProportion = 0;
            for (int i = 0; i < points.length; i++) {
                if (points[i] == points[index]) {
                    tempSum++;
                }
            }

            //1111111把当前积分相同的名次奖金比例相加1111111111
            for (int i = index; i < index+tempSum; i++) {
                sumDivideProportion = CalDouble.addDouble(sumDivideProportion,divideProportion[i]);
            }
            //1111111把当前积分相同的名次奖金比例相加1111111111
            index += tempSum;
            reward = CalDouble.subDouble(CalDouble.div(CalDouble.mul(sumDivideProportion , rewardPool) , index - lastIndex,2) , RegistrationFee);
            for (int i = 0; i < index - lastIndex; i++) {
                checkMoney.add(reward);
            }
            //当前积分计算完成后把当前游标赋值给lastIndex
            lastIndex = index;
        }
        return checkMoney;
    }

    @Override
    /**
     * 合并积分和账单
     */
    public List<PointsList> mergePointsAndAccount(List<PointsList> pointsDesc, List<Double> checkMoney) {
        for (int i = 0; i < checkMoney.size(); i++) {
            pointsDesc.get(i).setCheckMoney(checkMoney.get(i));
        };
        return pointsDesc;
    }
}
