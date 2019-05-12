package com.arcsoft.arcfacedemo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.arcsoft.arcfacedemo.activity.Main1Activity;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.File;

public class Down extends AppCompatActivity  implements OnDownloadListener {
private Button btn1 ,btn2;
    private NumberProgressBar progressBar;
    private DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        progressBar=findViewById(R.id.number_progress_bar);

   btn1=findViewById(R.id.down);
   btn2=findViewById(R.id.fanhui);
    btn1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startUpdate1();

        }
    });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Down.this,Main1Activity.class);
                startActivity(intent);
                finish();
            }
        });

        exitActivity();
    }











    private void startUpdate1() {

        final UpdateConfiguration configuration=new UpdateConfiguration()
                .setOnDownloadListener(Down.this);
        new AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage("版本更新")
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            DownloadManager.getInstance().release();

                            progressBar.setProgress(0);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        manager = DownloadManager.getInstance(Down.this);
                        manager.setApkName("app-debug.apk")




                                .setApkUrl("https://raw.githubusercontent.com/zhulihao1012/demo/master/app-debug.apk")
                                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                                .setConfiguration(configuration)
                                .setSmallIcon(R.mipmap.ic_launcher)

                                .download();
                    }
                }).create().show();
    }

    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {
        Message msg = new Message();
        msg.arg1 = max;
        msg.arg2 = progress;
        handler.sendMessage(msg);
    }

    @Override
    public void done(File apk) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void error(Exception e) {
//        LogUtil.e("down", "error: " + e);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setMax(msg.arg1);
            progressBar.setProgress(msg.arg2);
        }
    };

    private void exitActivity() {
        ExitApplication.getInstance().addActivity(Down.this);
    }


}
