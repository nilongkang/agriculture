package com.nlk.agriculture.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {

        public static String gettime() {
            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );//设置日期格式
            return df.format( new Date() ) ;// new Date()为获取当前系统时间

        }
}
