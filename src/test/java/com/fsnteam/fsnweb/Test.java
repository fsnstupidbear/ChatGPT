package com.fsnteam.fsnweb;

public class Test {
    @org.junit.Test
    public void test(){
        String test1="1,2,3,4";
        String[] split = test1.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }
}
