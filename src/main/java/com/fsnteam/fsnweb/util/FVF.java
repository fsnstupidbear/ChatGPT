package com.fsnteam.fsnweb.util;

import java.util.*;

/**
 * 1.随机生成对应数量的随机数（一般为8）randnums(int reqSum)
 * 2.将生成的序号交替分入红蓝队
 */
public class FVF {

    public static void divideGroup() {
        Integer[] randnums = randNums(8);//调用randNums生成需求数量随机数
        Map<String, List> teaminfo = divideRB(randnums);//调用divideRB对生成的随机数交替分组，取得返回值进入Map
        //从Map中取出红蓝队分配完毕后的成员编号
        List redTeam = teaminfo.get("redTeam");
        List blueTeam = teaminfo.get("blueTeam");
        System.out.println("红队：" + redTeam + "蓝队：" + blueTeam);
    }

    /**
     * 将生成的随机数交替分入红蓝队
     *
     * @param nums
     * @return
     */
    public static Map<String, List> divideRB(Integer[] nums) {
        List<Integer> redTeam = new ArrayList<Integer>();//红队队伍
        List<Integer> blueTeam = new ArrayList<Integer>();//蓝队队伍
        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 0) {
                redTeam.add(nums[i]);
            }
            if (i % 2 == 1) {
                blueTeam.add(nums[i]);
            }
        }
        //红蓝队分组信息存入Map
        Map<String, List> teaminfo = new HashMap<String, List>();
        teaminfo.put("redTeam", redTeam);
        teaminfo.put("blueTeam", blueTeam);
//        System.out.println("红队："+redTeam+"蓝队："+blueTeam);
        return teaminfo;
    }

    /**
     * 随机生成对应数量的不重复随机数
     */
    public static Integer[] randNums(int reqSum) {
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
    //把字符数组转换成整型数组
        public static int[] charArrayToIntArray (char[] s){
            int[] n=null;
        if(s.length!=0) {
            n = new int[s.length];
            for (int i = 0; i < s.length; i++) {
                n[i] = Integer.parseInt(String.valueOf(s[i]));
            }
        }
            return n;
        }
    }

