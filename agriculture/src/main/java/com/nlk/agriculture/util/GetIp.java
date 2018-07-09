package com.nlk.agriculture.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIp {

    public static String getip() throws UnknownHostException {
        // TODO Auto-generated method stub
        InetAddress ia = null;
        ia = ia.getLocalHost();
        String localname = ia.getHostName();
        String localip = ia.getHostAddress();
        return localip;
    }
}
