package com.arcsoft.arcfacedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.arcsoft.arcfacedemo.activity.ChooseFunctionActivity;
import com.arcsoft.arcfacedemo.activity.Main1Activity;

/*
* 开机自动启动应用
* */


public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent it = new Intent(context, Main1Activity.class);

            // 要启动的Activity  //1.如果自启动APP，参数为需要自动启动的应用包名
            //   intent = getPackageManager().getLaunchIntentForPackage("com.example.administrator.myapplication");
            // /下面这句话必须加上才能开机自动运行app的界面
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 2.如果自启动Activity
            context.startActivity(it);
            // 3.如果自启动服务 context.startService(intent); } } }
        }
    }

}
