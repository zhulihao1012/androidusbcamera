package com.arcsoft.arcfacedemo;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/*
* 退出应用
* */

public class ExitApplication extends Application {
    private List<Activity> activityList = new ArrayList<>();
    private static ExitApplication instance;

    public ExitApplication(){}
    public static ExitApplication getInstance(){
        if(null == instance){
            instance = new ExitApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity){
        //Log.i("activity", "size:" + activityList.size());
        //Log.i("activity", "name:" + activity.getIntent());
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit(){
        for(Activity activity : activityList){
            //依次关闭
            activity.finish();
            //Log.i("activity", "del_size:" + activityList.size());
            //Log.i("activity", "del_name:" + activity.getIntent());
        }
        //强制退出
        System.exit(0);
    }
}
