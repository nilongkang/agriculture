package com.nlk.agriculture.util;

import com.nlk.agriculture.domain.SysVideo;

import java.util.Comparator;

public class VideoHot implements Comparator {

    public int compare(Object obj0, Object obj1) {
        SysVideo sysVideo0=(SysVideo) obj0;
        SysVideo sysVideo1=(SysVideo) obj1;

        //首先比较点击量，如果点击量相同，则比较哪个最新

        int flag=sysVideo0.getClick_num().compareTo(sysVideo1.getClick_num());
        if(flag==0){
            return (0-sysVideo0.getAdd_time().compareTo(sysVideo1.getAdd_time()));
        }else{
            return (0-flag);
        }
    }

}