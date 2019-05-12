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
import android.widget.EditText;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.activity.FaceManageActivity;
import com.arcsoft.arcfacedemo.activity.Main1Activity;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.File;

public class SignOut extends AppCompatActivity implements OnDownloadListener{
    private NumberProgressBar progressBar;
    private DownloadManager manager;
    private EditText tv1,tv2;
    private Button button,btn1,arcface;
    final  static String zh="admin",pwd1="319319",pwd2="320320";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
   progressBar=findViewById(R.id.number_progress_bar);
        exitActivity();

        tv1= (EditText) findViewById(R.id.zh);
        tv2= (EditText) findViewById(R.id.pwd);
        button= (Button) findViewById(R.id.signout);
        Button bt2= (Button) findViewById(R.id.fanhui);
        arcface=findViewById(R.id.arcface);
        btn1= (Button) findViewById(R.id.down);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv1.getText().toString().equals(zh)&&tv2.getText().toString().equals(pwd1)) {
                    tz();
                }else {
                    Toast.makeText(SignOut.this,"账号或密码输入错误",Toast.LENGTH_LONG).show();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignOut.this,Main1Activity.class);
                startActivity(intent);

            }
        });
    btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (tv1.getText().toString().equals(zh)&&tv2.getText().toString().equals(pwd2)){

            Intent intent=new Intent(SignOut.this,Down.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(SignOut.this,"账号或密码输入错误",Toast.LENGTH_LONG).show();
        }
    }
});

arcface.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (tv1.getText().toString().equals(zh)&&tv2.getText().toString().equals(pwd2)){

        //    Intent intent=new Intent(SignOut.this,FaceManageActivity.class);
         //   startActivity(intent);
           //      finish();
        }else {
            Toast.makeText(SignOut.this,"账号或密码输入错误",Toast.LENGTH_LONG).show();
        }

    }
});

    }

    private void startUpdate1() {

        final UpdateConfiguration configuration=new UpdateConfiguration()
                .setOnDownloadListener(SignOut.this);
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


                        manager = DownloadManager.getInstance(SignOut.this);
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
        ExitApplication.getInstance().addActivity(SignOut.this);
    }

    private  void tz(){

        ExitApplication.getInstance().exit();


    }

}
