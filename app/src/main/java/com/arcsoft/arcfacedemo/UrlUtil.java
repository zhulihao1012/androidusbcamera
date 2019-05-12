package com.arcsoft.arcfacedemo;

public class UrlUtil {
/*
* 0为正式环境,1为测试环境,2为悉尼环境,3为广州局域网环境，4为南京局域网,5为智能仓库测试
* */
public static int  position=0;
    public static int  topicid;

    public  static   String urlarr[]=new String[]{
            "http://device.boohersmart.com/#/",
            "http://device2.boohersmart.com/#/",
            "http://sydney.boohersmart.com/#/",
            "http://10.172.15.190:188/#/",
            "http://192.168.1.194:188/#/",
            "http://192.168.8.100:188/#/"
    };

    public static String getjsonarr[]=new String[]{
            "http://www.boohersmart.com:3000/api/device/select/deviceId/by/",
            "http://www2.boohersmart.com:3000/api/device/select/deviceId/by/",
            "http://sydney.boohersmart.com:3000/api/device/select/deviceId/by/",
            "http://10.172.15.190:3000/api/device/select/deviceId/by/",
            "http://192.168.1.194:3000/api/device/select/deviceId/by/",
            "http://192.168.8.100:3000/api/device/select/deviceId/by/"
    };

    public static String hostarr[] =new  String[]{
            "tcp://www.boohersmart.com:1883",
            "tcp://www2.boohersmart.com:1883",
            "tcp://sydney.boohersmart.com:1883",
            "tcp://10.172.15.190:8083",
            "tcp://192.168.8.100:8083",
            "tcp://192.168.8.100:8083"
};



}
