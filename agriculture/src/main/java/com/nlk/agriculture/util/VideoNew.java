package com.nlk.agriculture.util;

import com.nlk.agriculture.domain.SysVideo;

import java.util.Comparator;

public class VideoNew implements Comparator {

    public int compare(Object obj0, Object obj1) {
        SysVideo sysVideo0=(SysVideo) obj0;
        SysVideo sysVideo1=(SysVideo) obj1;

        //首先比较上传时间，如果上传时间相同，则比较哪个最热

        int flag=sysVideo0.getAdd_time().compareTo(sysVideo1.getAdd_time());
        if(flag==0){
            return (0-sysVideo0.getClick_num().compareTo(sysVideo1.getClick_num()));
        }else{
            return (0-flag);
        }
    }

}