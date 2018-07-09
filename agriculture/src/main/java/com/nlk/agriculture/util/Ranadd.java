package com.nlk.agriculture.util;

import java.util.Random;

public class Ranadd {
    public static int random() {
        int x;//定义两变量
        Random ne=new Random();//实例化一个random的对象ne
        x=ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        return x;//输出
    }
}
