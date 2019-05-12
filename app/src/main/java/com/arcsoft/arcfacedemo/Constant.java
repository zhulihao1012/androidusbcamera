package com.arcsoft.arcfacedemo;

import android.os.Environment;

import java.io.File;

/**

 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class Constant {

    /**
     * 下载路径
     */
    public final static String DELETE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/arcfacedemo/";
    public final static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/arcfacedemo/register/";

    /**
     * 若文件下载不下来，更换网址
     */
//    public final static String URL_1 = "http://files.ibaodian.com/v2/teamfile/1ca447a600580cdcb575ab9348536f38/CM10086_android_V4.8.0_20180708_A0001.apk";
//    public final static String URL_2 = "http://files.ibaodian.com/v2/teamfile/f063d3c2c4a32a8143fc4f36be39cfd9/jtyh.patch";
    public final static String URL_3 = "http://192.168.31.123/thinkphp1/Uploads/";
//    public final static String URL_4 = "http://192.168.31.76/thinkphp1/Uploads/5bddc24b8a14b.jpg";
//    public final static String URL_5 = "http://192.168.31.76/thinkphp1/Uploads/5bddc24b8a14b.jpg";
//    public final static String URL_6 = "http://192.168.31.76/thinkphp1/Uploads/5bddc24b8a14b.jpg";
//    public final static String URL_7 = "http://files.ibaodian.com/v2/teamfile/ac43a96d0f21e83cd3967e60e6775d1d/sf_updata.apk";
//    public final static String URL_8 = "http://files.ibaodian.com/v2/teamfile/长城金禧利年金保险菁华版（A计划）.pdf";
//    public final static String URL_9 = "http://files.ibaodian.com/v2/teamfile/2b1d7f518fbcf467ec9bf748743bea80/D90B2EA927372212B33BB673318AA1A1361024EB20B8493A9B23E8178DF3D001";

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        File file = new File(FILE_PATH + fileName);
        if (file.exists()){
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        }else
            status = false;
        return status;
    }

}
