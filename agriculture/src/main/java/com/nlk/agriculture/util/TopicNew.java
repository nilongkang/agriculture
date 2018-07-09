package com.nlk.agriculture.util;

import com.nlk.agriculture.domain.SysTopic;

import java.util.Comparator;

public class TopicNew implements Comparator {

    public int compare(Object obj0, Object obj1) {
        SysTopic sysTopic0=(SysTopic) obj0;
        SysTopic sysTopic1=(SysTopic) obj1;

        //首先比较点击量，如果点击量相同，则比较哪个最新

        int flag=sysTopic0.getAdd_time().compareTo(sysTopic0.getAdd_time());
        if(flag==0){
            return (0-sysTopic1.getClick_num().compareTo(sysTopic1.getClick_num()));
        }else{
            return (0-flag);
        }
    }
}
