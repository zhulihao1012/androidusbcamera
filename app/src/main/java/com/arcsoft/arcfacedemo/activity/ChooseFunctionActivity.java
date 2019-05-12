package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.ExitApplication;
import com.arcsoft.arcfacedemo.MQTTService;
import com.arcsoft.arcfacedemo.MultipleActivity;
import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;




public class ChooseFunctionActivity extends AppCompatActivity {
    public static final String url = "http://192.168.31.123/thinkphp1/index.php/Image/upload";
    //  public static final String url = "http://www.baidu.com";
    private Toast toast = null;
    private Button btn, register, down;

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_function);
        btn = findViewById(R.id.up);
        //   register=findViewById(R.id.register);
        down = findViewById(R.id.down);
        Intent intent = new Intent(ChooseFunctionActivity.this, MQTTService.class);
        startService(intent);


        Intent  intent1 = new Intent(ChooseFunctionActivity.this, MQTTService.class);
        startService(intent1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(ChooseFunctionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(ChooseFunctionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {

                        // actionStart(ChooseFunctionActivity.this, 10, null, url);

                    }
                } else {
//这个说明系统版本在6.0之下，不需要动态获取权限。
                    //    actionStart(ChooseFunctionActivity.this, 10, null, url);
                }

            }
        });

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.down:

                        startActivity(new Intent(ChooseFunctionActivity.this, MultipleActivity.class));


                        break;

                    case R.id.register:

                        startActivity(new Intent(ChooseFunctionActivity.this, FaceManageActivity.class));

                        break;
                    default:
                        break;

                }
                return true;
            }
        });


        initView();
        exitActivity();
        activeEngine();


    }

    private void initView() {
        //设置视频模式下的人脸优先检测方向
//        RadioGroup radioGroupFtOrient = findViewById(R.id.radio_group_ft_orient);
//        RadioButton rbOrient0 = findViewById(R.id.rb_orient_0);
//        RadioButton rbOrient90 = findViewById(R.id.rb_orient_90);
//        RadioButton rbOrient180 = findViewById(R.id.rb_orient_180);
//        RadioButton rbOrient270 = findViewById(R.id.rb_orient_270);
//        RadioButton rbOrientAll = findViewById(R.id.rb_orient_all);
//        switch (ConfigUtil.getFtOrient(this)) {
//            case FaceEngine.ASF_OP_0_ONLY:
//                rbOrient0.setChecked(true);
//                break;
//            case FaceEngine.ASF_OP_90_ONLY:
//                rbOrient90.setChecked(true);
//                break;
//            case FaceEngine.ASF_OP_180_ONLY:
//                rbOrient180.setChecked(true);
//                break;
//            case FaceEngine.ASF_OP_270_ONLY:
//                rbOrient270.setChecked(true);
//                break;
//            case FaceEngine.ASF_OP_0_HIGHER_EXT:
//                rbOrientAll.setChecked(true);
//                break;
//            default:
//                rbOrient0.setChecked(true);
//                break;


        //   }
//        radioGroupFtOrient.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_orient_0:
//                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_ONLY);
//                        break;
//                    case R.id.rb_orient_90:
//                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_90_ONLY);
//                        break;
//                    case R.id.rb_orient_180:
//                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_180_ONLY);
//                        break;
//                    case R.id.rb_orient_270:
//                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_270_ONLY);
//                        break;
//                    case R.id.rb_orient_all:
//                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
//                        break;
//                    default:
//                        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_ONLY);
//                        break;
//                }
//            }
//        });

        ConfigUtil.setFtOrient(ChooseFunctionActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
    }

    /**
     * 打开相机，显示年龄性别
     *
     * @param view
     */
    public void jumpToPreviewActivity(View view) {
        startActivity(new Intent(this, PreviewActivity.class));
    }

    /**
     * 处理单张图片，显示图片中所有人脸的信息，并且一一比对相似度
     *
     * @param view
     */
    public void jumpToSingleImageActivity(View view) {
        startActivity(new Intent(this, SingleImageActivity.class));
    }

    /**
     * 选择一张主照，显示主照中人脸的详细信息，然后选择图片和主照进行比对
     *
     * @param view
     */
    public void jumpToMultiImageActivity(View view) {
        startActivity(new Intent(this, MultiImageActivity.class));
    }

    /**
     * 打开相机，人脸注册，人脸识别
     *
     * @param view
     */
    public void jumpToFaceRecognizeActivity(View view) {
        startActivity(new Intent(this, RegisterAndRecognizeActivity.class));
//        Intent intent=new Intent(ChooseFunctionActivity.this,RegisterAndRecognizeActivity.class);
//        startActivity(intent);
    }

    /**
     * 批量注册和删除功能
     *
     * @param view
     */
    public void jumpToBatchRegisterActivity(View view) {
        startActivity(new Intent(this, FaceManageActivity.class));
    }


    public void RegActivity(View view) {
        startActivity(new Intent(this, RegActivity.class));
    }


    public void down(View view) {

        //   Intent intent=new Intent(ChooseFunctionActivity.this,MultipleActivity.class);
        startActivity(new Intent(this, MultipleActivity.class));
    }

//    public  void  up(View view){
//
//        //   Intent intent=new Intent(ChooseFunctionActivity.this,MultipleActivity.class);
//        actionStart(ChooseFunctionActivity.this, 10, null, url);
//    }


    /**
     * 激活引擎
     *
     * @param //view
     */
    // public void activeEngine(final View view) {
    public  void activeEngine() {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
//        if (view != null) {
//            view.setClickable(false);
//        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
              int activeCode = faceEngine.active(ChooseFunctionActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }

//                        if (view != null) {
//                            view.setClickable(true);
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
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
                activeEngine();
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }

    private void exitActivity() {
        ExitApplication.getInstance().addActivity(ChooseFunctionActivity.this);
    }


}