package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.Constant;
import com.arcsoft.arcfacedemo.ExitApplication;
import com.arcsoft.arcfacedemo.MessageEvent;
import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.SignOut;
import com.arcsoft.arcfacedemo.download.DownloadManager;
import com.arcsoft.arcfacedemo.download.DownloadObserver;
import com.arcsoft.arcfacedemo.util.Delete;
import com.arcsoft.arcfacedemo.widget.ProgressDialog;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.util.ImageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 批量注册页面
 */
public class FaceManageActivity extends AppCompatActivity {
    //注册图所在的目录
    private static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "arcfacedemo";
    private static final String REGISTER_DIR = ROOT_DIR + File.separator + "register";
    private static final String REGISTER_FAILED_DIR = ROOT_DIR + File.separator + "failed";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
private  int i;
    private TextView tvNotificationRegisterResult;
    private Button btn,fanhui;
    public static String date;
    private List list3=new ArrayList();
    private List list4=new ArrayList();

    ProgressDialog progressDialog = null;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_manage);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tvNotificationRegisterResult = findViewById(R.id.notification_register_result);
        progressDialog = new ProgressDialog(this);
        FaceServer.getInstance().init(this);
        init();
        EventBus.getDefault().register(this);
        btn=findViewById(R.id.btn);
        fanhui=findViewById(R.id.fanhui);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for ( i=0;i<list3.size();i++) {

                    DownloadManager.getInstance().download((String) list3.get(i), new DownloadObserver());


                }



            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FaceManageActivity.this,SignOut.class);
                startActivity(intent);
                finish();
            }
        });
        exitActivity();
    }

    @Override
    protected void onDestroy() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        FaceServer.getInstance().unInit();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    public void batchRegister(View view) {
        if (checkPermissions(NEEDED_PERMISSIONS)) {
            doRegister();
        } else {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        }
    }

    private void doRegister() {
        File dir = new File(REGISTER_DIR);
        if (!dir.exists()) {
            Toast.makeText(this, "文件夹\n" + REGISTER_DIR + "\n 不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!dir.isDirectory()) {
            Toast.makeText(this, "路径 \n" + REGISTER_DIR + "\n 不是目录", Toast.LENGTH_SHORT).show();
            return;
        }
        final File[] jpgFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(FaceServer.IMG_SUFFIX);
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final int totalCount = jpgFiles.length;

                int successCount = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMaxProgress(totalCount);
                        progressDialog.show();
                        tvNotificationRegisterResult.setText("");
                        tvNotificationRegisterResult.append("开始注册,请等待\n\n");
                    }
                });
                for (int i = 0; i < totalCount; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressDialog != null) {
                                progressDialog.refreshProgress(finalI);
                            }
                        }
                    });
                    final File jpgFile = jpgFiles[i];
                    Bitmap bitmap = BitmapFactory.decodeFile(jpgFile.getAbsolutePath());
                    if (bitmap == null) {
                        File failedFile = new File(REGISTER_FAILED_DIR + File.separator + jpgFile.getName());
                        if (!failedFile.getParentFile().exists()) {
                            failedFile.getParentFile().mkdirs();
                        }
                        jpgFile.renameTo(failedFile);
                        continue;
                    }
                    bitmap = ImageUtil.alignBitmapForNv21(bitmap);
                    if (bitmap == null) {
                        File failedFile = new File(REGISTER_FAILED_DIR + File.separator + jpgFile.getName());
                        if (!failedFile.getParentFile().exists()) {
                            failedFile.getParentFile().mkdirs();
                        }
                        jpgFile.renameTo(failedFile);
                        continue;
                    }
                    byte[] nv21 = ImageUtil.bitmapToNv21(bitmap, bitmap.getWidth(), bitmap.getHeight());
                    boolean success = FaceServer.getInstance().register(FaceManageActivity.this, nv21, bitmap.getWidth(), bitmap.getHeight(),
                            jpgFile.getName().substring(0, jpgFile.getName().lastIndexOf(".")));
                    if (!success) {
                        File failedFile = new File(REGISTER_FAILED_DIR + File.separator + jpgFile.getName());
                        if (!failedFile.getParentFile().exists()) {
                            failedFile.getParentFile().mkdirs();
                        }
                        jpgFile.renameTo(failedFile);
                    } else {
                        successCount++;
                    }
                }
                final int finalSuccessCount = successCount;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
//                        tvNotificationRegisterResult.append("process finished!\ntotal count = " + totalCount + "\nsuccess count = " + finalSuccessCount + "\nfailed count = " + (totalCount - finalSuccessCount)
//                                + "\nfailed images are in directory '" + REGISTER_FAILED_DIR + "'");
                        tvNotificationRegisterResult.append("注册完成!\n总计数 = " + totalCount + "\n成功计数= " + finalSuccessCount + "\n失败计数 = " + (totalCount - finalSuccessCount)
                                + "\n注册失败的图像在文件夹'" + REGISTER_FAILED_DIR + "'");
                    }
                });
                Log.i(FaceManageActivity.class.getSimpleName(), "run: " + executorService.isShutdown());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                doRegister();
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this.getApplicationContext(), neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    public void clearFaces(View view) {
        Delete.deleteFolderFile(Constant.FILE_PATH,true);
        int faceNum = FaceServer.getInstance().getFaceNumber(this);
        if (faceNum == 0){
            Toast.makeText(this, R.string.no_face_need_to_delete, Toast.LENGTH_SHORT).show();
        }else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.notification)
                    .setMessage(getString(R.string.confirm_delete,faceNum)  )
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int deleteCount = FaceServer.getInstance().clearAllFaces(FaceManageActivity.this);
                            Toast.makeText(FaceManageActivity.this, deleteCount + " 个人脸信息已清空!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel,null)
                    .create();
            dialog.show();
        }
      //  Delete.deleteFolderFile(Constant.FILE_PATH,true);//删除人脸图片

    }
    public void init() {
        new Thread(new Runnable() {
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回的地址
                Request request = new Request.Builder()
                        .url("http://www2.boohersmart.com:3000/api/admin_device/select/face/image/021807001").build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    //获取到数据
                    date = response.body().string();
                    //在线程中没有办法实现主线程操作

                    JSONObject jsonObject=new JSONObject(date);


                    //  JSONObject    jsonObject = new JSONObject(date);
                    String        get_image = jsonObject.getString("data");
                    //  getdevice_id.setText("access_token" + get_uuid);

                    JSONArray jsonArray = new JSONArray(get_image);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        String imageurl  = jsonObject1.getString("imageUrl");
                        list3.add(imageurl);


                    }


                } catch (IOException e) {


                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setEvent(MessageEvent messageEvent){
        //  getdevice_id.setText(messageEvent.getMessage());
        String a=messageEvent.getMessage();
        list4.add(a);

        Toast.makeText(FaceManageActivity.this,"第"+list4.size()+"张人脸图片下载完成,共"+list3.size()+"张图片",Toast.LENGTH_SHORT).show();
        if (list4.size()==list3.size()){
            Toast.makeText(FaceManageActivity.this,"下载完成",Toast.LENGTH_SHORT).show();

        }
    }



    private void exitActivity() {
        ExitApplication.getInstance().addActivity(FaceManageActivity.this);
    }
}
