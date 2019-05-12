package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.Constant;
import com.arcsoft.arcfacedemo.DeviceMessageUtil;
import com.arcsoft.arcfacedemo.ExitApplication;
import com.arcsoft.arcfacedemo.MQTTService;
import com.arcsoft.arcfacedemo.MessageEvent;
import com.arcsoft.arcfacedemo.MultipleActivity;
import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.SignOut;
import com.arcsoft.arcfacedemo.download.DownloadInfo;
import com.arcsoft.arcfacedemo.download.DownloadManager;
import com.arcsoft.arcfacedemo.download.DownloadObserver;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.Delete;
import com.arcsoft.arcfacedemo.util.ImageUtil;
import com.arcsoft.arcfacedemo.util.camera.CameraHelper;
import com.arcsoft.arcfacedemo.widget.ProgressDialog;
import com.arcsoft.face.FaceEngine;

import org.eclipse.paho.client.mqttv3.MqttException;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;

import static com.arcsoft.arcfacedemo.MQTTService.client;
import static com.arcsoft.arcfacedemo.UrlUtil.getjsonarr;
import static com.arcsoft.arcfacedemo.UrlUtil.position;
import static com.arcsoft.arcfacedemo.UrlUtil.topicid;
import static com.arcsoft.arcfacedemo.UrlUtil.urlarr;
import static com.arcsoft.arcfacedemo.download.DownloadObserver.downloadInfo;
import static com.arcsoft.arcfacedemo.util.camera.CameraHelper.rotation;


/*
* webview加载
* */

public class Main1Activity extends AppCompatActivity {


    public static  DisplayMetrics metrics;
    public static SharedPreferences preferences;
    public static final String APP_ID = "10123710";
    public static final String API_KEY = "Gd9bxXsl7yyw8HWDkpnb8q0C";
    public static final String SECRET_KEY = "xi8d3x9xsMXAuLnLznWsiv0CwZATBPHI";
    private  int finalSuccessCount;
    SharedPreferences.Editor editor;
    private TextView responseText;
    private View mErrorView;
    private int type;
    private  Dialog dialog1;
    private List list2 =new ArrayList();
    public Context context;
    private int faceNum1;
    public static final int UP = 1;
    private TextView xsdi;
    private JSONObject jsonObject1;
    private  int totalCount;
    private  int jsonerror1;
    public static List list1=new ArrayList();
    private int fanumsm;
    private  File jpgFile111;
    private  List list5=new ArrayList();
    private  List list6=new ArrayList();
    private  List list7=new ArrayList();
    public static String date;
    private  String s;
    private String device_type;
    private TextView xsimei;
    private  FrameLayout frameLayout;
    private DrawerLayout drawerLayout;

    private Intent intent;
    private TextView tv1,tv2,tv3;
    private  JSONArray jsonArray;
    public String responseData;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private String TAG = "vehicle_detect";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private WebView webView;
    private NetworkInfo networkInfo;
    public static String device_id = null;
    public static String did=null;
    private static String imei = null;
    private  static String uuid;
    private int i;
    private MyWebView myWebView;
    private String get_uuid;

    private String getjson1="http://www.boohersmart.com/api/device/select/deviceId/by/";//正式环境
    private String getjson2="http://www2.boohersmart.com:3000/api/device/select/deviceId/by/";//测试环境
    private String getjson3="http://10.172.15.190:3000/api/device/select/deviceId/by/";//局域网环境
    private  String URl1="http://device.boohersmart.com/#/";//正式环境
    private  String URl2="http://device2.boohersmart.com/#/";//测试环境
    private  String URl3="http://10.172.15.190:188/#/";//局域网
    int serverIndex=1;
//    private  String urlarr[]=new String[]{
//            "http://device.boohersmart.com/#/",
//            "http://device2.boohersmart.com/#/",
//            "http://device3.boohersmart.com/#/",
//            "http://10.172.15.190:188/#/",
//    };
//
//    private  String getjsonarr[]=new String[]{
//            "http://www.boohersmart.com/api/device/select/deviceId/by/",
//            "http://www2.boohersmart.com:3000/api/device/select/deviceId/by/",
//            "http://sydney.boohersmart.com:3000/api/device/select/deviceId/by/",
//            "http://10.172.15.190:3000/api/device/select/deviceId/by/"
//
//    };

    private FrameLayout webParentView,uuidView,nullView,loadView,netView;
    private Button btn;
    private  TextView fanum;
    private String jsonerror;
    private JSONObject jsonObject;
    private   TextView regnum,getdevice_id,getdevice_id1;

    //********人脸识别*************
    ProgressDialog progressDialog = null;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Mqttservicemessage mqttservicemessage;
    private IntentFilter intentFilter1;
    private static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "arcfacedemo";
    private static final String REGISTER_DIR = ROOT_DIR + File.separator + "register";
    private static final String REGISTER_FAILED_DIR = ROOT_DIR + File.separator + "failed";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private TextView tvNotificationRegisterResult;



    //主线程加载webview网页,开启mqtt服务
    private Handler handler=new Handler(){
        public void  handleMessage(Message msg){
            switch (msg.what){
                case  UP:
/**
 * 加载webview网页，开启mqtt
 * */

                //    metrics = getApplicationContext().getResources().getDisplayMetrics();
                 //  Toast.makeText(Main1Activity.this,"6",Toast.LENGTH_SHORT).show();

                    //     if (device_id==null){

                    //   showErrorPage();
                    //     webView.setVisibility(View.INVISIBLE);

                    //   }
                    //        xsimei.setText("设备号:"+device_id);

                    //            if (device_id!=null) {
                    //    xsimei.setText("设备号:"+device_id);
//showErrorPage();
//webView.setVisibility(View.INVISIBLE);
                    //     nullView.removeAllViews();
                //    long start = System.currentTimeMillis();
/**
 * 获取deviceid后,访问人脸图片api
 *
 * */
                    devicetype();

                    init();

                    webView.loadUrl(urlarr[position]+device_id);
                 //   webView.loadUrl("http://img5.imgtn.bdimg.com/it/u=3614534200,1549941437&fm=26&gp=0.jpg");
              //      Log.e("'long",""+(System.currentTimeMillis() - start));
                    intent = new Intent(Main1Activity.this, MQTTService.class);
                    startService(intent);



//                    networkChangeReceiver = new NetworkChangeReceiver();
//                    registerReceiver(networkChangeReceiver, intentFilter);
                    //                       }
                    //             }else {

                    //  webview();
//                            xsimei.setText("未获取到device_id");

//nulldevice();
//    addview();


                    //     }
                    break;

                case 567:
/**
 * 设备未添加deviceid报错
 * */
//                    if (jsonerror1==1000){
//                        Log.e("d1", "d1");
//                    }
                    if (jsonArray.length()==0){
                        nulldevice();
                        addview();
                        tv1.setText("请检查此uuid是否添加了设备号");
                    }else {
                        nulldevice();
                        addview();
                        tv1.setText(jsonerror);
                    }
//                    if (jsonObject1==null){
//                        nulldevice();
//                    }

//                    Log.e("error",jsonerror);
//if (jsonerror!=null) {
//    nulldevice();
//    addview();
//}
//if (i<2){
//    try {
//        imeiimsi();
//    } catch (NoSuchMethodException e) {
//        e.printStackTrace();
//    } catch (IllegalAccessException e) {
//        e.printStackTrace();
//    } catch (InvocationTargetException e) {
//        e.printStackTrace();
//    }
//    webview();

//}

                    break;
                case  369:

/**ping网络连接，成功开始运行程序
 * 失败重新ping网络，等待网络连接
 * 适用于设备重启后，软件自动启动，而网络还未连接
 * 在无网络状态下不运行程序
 *
 * */
                    if (s=="success"){
                        if(device_id==null) {
                            webview();
                          //  webView.loadUrl("http://www.baidu.com");

                        }else if (device_id!=null){
                            webView.loadUrl(urlarr[position]+device_id);

                        }
                    }else if(s=="faild"){
                        new NetPing().execute();

                    }
                    break;
                case 741:
                    network();
                    break;
                case 404:
                    nulldevice();
                    tv1.setText("api连接超时");
                    break;

                case 852:
                   Log.e("device_type",device_type.substring(1,2));
                    if (device_type.length()>5) {
                         type = Integer.parseInt(device_type.substring(1, 2));
                    }
                        if (type == 4 || type == 5) {
                            fanum.setText("人脸库总数:"+list1.size());
                            final int faceNum = FaceServer.getInstance().getFaceNumber(Main1Activity.this);
                            regnum.setText("已注册人脸人数:"+faceNum);
                            addarc();
                            addarcid();

                        Log.e("sh","bwk");
                    }
                    break;
                default:
                    break;
            }
        }

    };


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  xsdi = (TextView) findViewById(R.id.text);
        Log.e("321","123");
        initView();
        initWebView();
        drawerLayout=findViewById(R.id.drawer_layout);


        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
//        intent = new Intent(MainActivity.this, MQTTService.class);
//        startService(intent);


        preferences = getSharedPreferences("device_id", MODE_PRIVATE);
        editor = preferences.edit();
        intentFilter1 = new IntentFilter();
        intentFilter1.addAction("android.MQTTService.message");
        mqttservicemessage = new Mqttservicemessage();
        registerReceiver(mqttservicemessage, intentFilter1);

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ContextCompat.checkSelfPermission(Main1Activity.this, Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                //申请WRITE_EXTERNAL_STORAGE权限
//                ActivityCompat.requestPermissions(Main1Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA},
//                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//            } else {
//
//                try {
//                    imeiimsi();
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        } else {
////这个说明系统版本在6.0之下，不需要动态获取权限。
//            try {
//                imeiimsi();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
             new  NetPing().execute();
        try {
            imeiimsi();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        NavigationView navView = (NavigationView) findViewById(R.id.na_view);
        navView.setItemIconTintList(null);
        View headerView = navView.getHeaderView(0);

        xsimei = (TextView) headerView.findViewById(R.id.xsimei);
        xsimei.setText("uuid:" + uuid);
        fanum=headerView.findViewById(R.id.facenum);

        regnum=headerView.findViewById(R.id.regnum);

        getdevice_id=headerView.findViewById(R.id.getdevice_id);
        getdevice_id1=headerView.findViewById(R.id.getdevice_id1);
        // btn=headerView.findViewById(R.id.btn);



        // xsdi.setText(imei);

        exitActivity();


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_call:


                        Dialog dialog = new AlertDialog.Builder(Main1Activity.this)

                                .setTitle("退出") // 创建标题

                                .setMessage("您确定要退出应用吗？") //表示对话框的内容

//.setIcon(R.drawable.ic_launcher)//设置LOGO

                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                     Intent intent=new Intent(Main1Activity.this,SignOut.class);
                                        startActivity(intent);
                                               finish();

                                    }

                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                    }

                                }).create(); //创建对话框

                        dialog.show(); //显示对话框

                        break;

                    case R.id.shuaxin:

                        // webView.reload();
                        webView.reload();



                        break;



                    case  R.id.zzzz:
                      Intent intent=new Intent(Main1Activity.this,RegisterAndRecognizeActivity.class);
                      startActivity(intent);

                           break;
                    default:
                        break;
                }


                return true;
            }
        });





//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容
//        webSettings.setLoadWithOverviewMode(true);
//
//        //webView.loadUrl("http://www.baidu.com");
//        //跳转至webview
//        webSettings.setSupportZoom(true);//缩放开关
//        // webSettings.setBuiltInZoomControls(true);//设置是否可缩放
//        //    webView.getSettings().setDisplayZoomControls(false);//隐藏缩放工具
//        webSettings.setUseWideViewPort(true);//适应手机屏幕
//        //   webView.getSettings().setDomStorageEnabled(true);//设置DOM Storage缓存
//        webView.setInitialScale(25);
//
//        //  webView.getSettings().setDomStorageEnabled(true);
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭加速
//        //3.12 webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//默认关闭缓存
//        webView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
//
//
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//
//        final int mDensity = metrics.densityDpi;
//
//
//        if (mDensity == 120) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        } else if (mDensity == 160) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else if (mDensity == 240) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }
//
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loadbackground();
//                //  webView.setBackgroundResource(R.drawable.timg);
//                webView.setVisibility(View.INVISIBLE);
//            }
//
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
//                super.onReceivedError(webView, webResourceRequest, webResourceError);
//                if (webResourceRequest.isForMainFrame()) {
////                    ivError.setVisibility(View.VISIBLE);
//                    //   t1.setVisibility(View.VISIBLE);
//
//                    showErrorPage();
//
//
//                    webView.setVisibility(View.INVISIBLE);
//
//                }
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//                webView.setVisibility(View.VISIBLE);
//
//
//
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url == null) return false;
//                try {
//                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(intent);
//                        return true;
//                    }
//
//                } catch (Exception e) {
//                    return true;
//                }
//                view.loadUrl(url);
//                return true;
//            }
//
//
//
//        });
//
//        webView.setWebChromeClient(new WebChromeClient(){
//
//            public void onProgressChanged(WebView view,int progress){
//                super.onProgressChanged(view,progress);
//                if (progress==100){
//                    Log.e("","我完成啦");
////                    try {
////                        Thread.sleep(5000);
////                        if (i<2){
////                            Log.e("","yunxinglmei");
////                            webView.reload();
////                            i= i+1;
////                        }
////
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//
//                }
//            }
//
//        });






//**************人脸识别****************
        ConfigUtil.setFtOrient(Main1Activity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       // tvNotificationRegisterResult = findViewById(R.id.notification_register_result);
        progressDialog = new ProgressDialog(this);
        FaceServer.getInstance().init(this);//本地人脸库初始化



        EventBus.getDefault().register(this);

    }


    private void initView(){
        frameLayout=findViewById(R.id.fra);
    }

    private  void initWebView(){
        webView=new WebView(this);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(layoutParams);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容
        webSettings.setLoadWithOverviewMode(true);

        //webView.loadUrl("http://www.baidu.com");
        //跳转至webview
        webSettings.setSupportZoom(true);//缩放开关
        // webSettings.setBuiltInZoomControls(true);//设置是否可缩放
        //    webView.getSettings().setDisplayZoomControls(false);//隐藏缩放工具
        webSettings.setUseWideViewPort(true);//适应手机屏幕
        //   webView.getSettings().setDomStorageEnabled(true);//设置DOM Storage缓存
        webView.setInitialScale(25);

        //  webView.getSettings().setDomStorageEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭加速
        //3.12 webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//默认关闭缓存
        webView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        final int mDensity = metrics.densityDpi;


        if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadbackground();
                //  webView.setBackgroundResource(R.drawable.timg);
                webView.setVisibility(View.INVISIBLE);
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                if (webResourceRequest.isForMainFrame()) {
//                    ivError.setVisibility(View.VISIBLE);
                    //   t1.setVisibility(View.VISIBLE);

                    showErrorPage();


                    webView.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.setVisibility(View.VISIBLE);



            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }

                } catch (Exception e) {
                    return true;
                }
                view.loadUrl(url);
                return true;
            }



        });

        webView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView view,int progress){
                super.onProgressChanged(view,progress);
                if (progress==100){
                    Log.e("","我完成啦");
//                    try {
//                        Thread.sleep(5000);
//                        if (i<2){
//                            Log.e("","yunxinglmei");
//                            webView.reload();
//                            i= i+1;
//                        }
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                }
            }

        });
        frameLayout.removeAllViews();
        frameLayout.addView(webView);

    }


    public static class MyWebView extends WebView{
        public interface PlayFinish{
            void After();
        }
        PlayFinish df;
        public void setDf(PlayFinish playFinish) {
            this.df = playFinish;
        }
        public MyWebView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public MyWebView(Context context) {
            super(context);
        }
        //onDraw表示显示完毕
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            df.After();
        }
    }

/**
 * 获取IMEI，uuid
 * */
    private void imeiimsi() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        imei=DeviceMessageUtil.getEI_SI("",this);
        uuid = DeviceMessageUtil.getUniqueID(this);
        Log.e("uuid",uuid);


    }



    private  void addview(){
        final LinearLayout relativeLayout=findViewById(R.id.rel);
        final Button btn=new Button(Main1Activity.this);
        btn.setText("重新获取device_id");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imeiimsi();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                webview();

                relativeLayout.removeView(btn);

            }
        });


        relativeLayout.addView(btn);

    }


    private  void addarcid(){
        final LinearLayout relativeLayout=findViewById(R.id.rel);
        final Button btn=new Button(Main1Activity.this);
        btn.setText("人脸编号");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File path = new File(Constant.FILE_PATH);
                final File[] jpgFiles = path.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(FaceServer.IMG_SUFFIX);
                    }
                });
                final int faceNum = FaceServer.getInstance().getFaceNumber(Main1Activity.this);

                for (int x=0;x<faceNum;x++) {
                    jpgFile111 = jpgFiles[x];
                    String faceid=jpgFile111.getName().substring(0,jpgFile111.getName().indexOf(".jpg"));
                    list5.add(faceid);

                }

                for (int y=0;y<list1.size();y++ ){
                    String urlid=list1.get(y).toString().substring(list1.get(y).toString().indexOf("faceImage/"));
                    String facenum=urlid.substring(10,urlid.indexOf(".jpg"));
                    list6.add(facenum);

                    //     Log.e("list6",list6.toString());

                }
//                        list6.add(1);
//                        list6.add(6);
//                        list6.add(360);
//                        list6.add(1954);
//                        Log.e("list6",list6.toString());

                for (Object str1:list6) {
                    if (!list5.contains(str1)) {

                        list7.add(str1);
                    }
                }


                String[] items = {"已注册faceId:"+list5.toString(),"未注册faceId:"+list7.toString() };
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(Main1Activity.this);

                dialog2    .setTitle("faceId") ;// 创建标题


                dialog2.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

//.setIcon(R.drawable.ic_launcher)//设置LOGO

//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                            Intent intent = new Intent(Main1Activity.this, SignOut.class);
//                                            startActivity(intent);
//                                            finish();
//
//                                        }
//
//                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//
//                                    }).create(); //创建对话框

                dialog2.show(); //显示对话框
                list7.clear();
                list6.clear();
                list5.clear();

            }
        });


        relativeLayout.addView(btn);

    }



    private  void addarc(){
        final LinearLayout relativeLayout=findViewById(R.id.rel);
        final Button btn=new Button(Main1Activity.this);
        btn.setText("更新人脸库");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearFaces();


                list2.clear();

                downimage();
                Toast.makeText(Main1Activity.this,"开始下载人脸库",Toast.LENGTH_SHORT).show();
                regnum.setText("已注册人脸总数:"+0);


            }
        });


        relativeLayout.addView(btn);

    }









    protected void network() {
        netView = (FrameLayout) webView.getParent();
        networkPage();//初始化自定义页面
        while (netView.getChildCount() > 1) {
            netView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        netView.addView(mErrorView, 0, lp);

        //    mIsErrorPage = true;
    }


    protected void networkPage() {

        mErrorView = View.inflate(this, R.layout.activity_network, null);

//        RelativeLayout layout = (RelativeLayout) mErrorView.findViewById(R.id.online_error_btn_retry);
////            LinearLayout lay=mErrorView.findViewById(R.id.layout);
//        //    LinearLayout lay1=mErrorView.findViewById(R.id.layout1);
//        layout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                webView.reload();
//            }
//        });

    }





    protected void nulldevice() {
        nullView = (FrameLayout) webView.getParent();
        nulldeviceidPage();//初始化自定义页面
        while (nullView.getChildCount() > 1) {
            nullView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        nullView.addView(mErrorView, 0, lp);

        //    mIsErrorPage = true;
    }


    protected void nulldeviceidPage() {

        mErrorView = View.inflate(this, R.layout.activity_nulldevice_id, null);
        tv1=mErrorView.findViewById(R.id.json);
        tv2=mErrorView.findViewById(R.id.data);
        tv3=mErrorView.findViewById(R.id.uui);
//tv3.setText("lllllll");
////tv3.setText("device_id:"+device_id);
//        tv2.setText("json:"+jsonObject);
//        tv1.setText("error:"+jsonerror);
//        RelativeLayout layout = (RelativeLayout) mErrorView.findViewById(R.id.online_error_btn_retry);
////            LinearLayout lay=mErrorView.findViewById(R.id.layout);
//        //    LinearLayout lay1=mErrorView.findViewById(R.id.layout1);
//        layout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                webView.reload();
//            }
//        });

    }



    protected void loadbackground() {
        loadView = (FrameLayout) webView.getParent();
        backgroubdPage();//初始化自定义页面
        while (loadView.getChildCount() > 1) {
            loadView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        loadView.addView(mErrorView, 0, lp);
        //    mIsErrorPage = true;
    }


    protected void backgroubdPage() {

        mErrorView = View.inflate(this, R.layout.android_background, null);
//        RelativeLayout layout = (RelativeLayout) mErrorView.findViewById(R.id.online_error_btn_retry);
////            LinearLayout lay=mErrorView.findViewById(R.id.layout);
//        //    LinearLayout lay1=mErrorView.findViewById(R.id.layout1);
//        layout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                webView.reload();
//            }
//        });
    }

    protected void getuuid() {
        uuidView = (FrameLayout) webView.getParent();
        uuiderror();//初始化自定义页面
        while (uuidView.getChildCount() > 1) {
            uuidView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        uuidView.addView(mErrorView, 0, lp);
        //    mIsErrorPage = true;
    }


    protected void uuiderror() {

        mErrorView = View.inflate(this, R.layout.uuid_error, null);
//        RelativeLayout layout = (RelativeLayout) mErrorView.findViewById(R.id.online_error_btn_retry);
////            LinearLayout lay=mErrorView.findViewById(R.id.layout);
//        //    LinearLayout lay1=mErrorView.findViewById(R.id.layout1);
//        layout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                webView.reload();
//            }
//        });
    }




    protected void showErrorPage() {
        webParentView = (FrameLayout) webView.getParent();
        initErrorPage();//初始化自定义页面
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        webParentView.addView(mErrorView, 0, lp);
        //    mIsErrorPage = true;
    }

    protected void initErrorPage() {

        mErrorView = View.inflate(this, R.layout.activity_error, null);
        RelativeLayout layout = (RelativeLayout) mErrorView.findViewById(R.id.online_error_btn_retry);
//            LinearLayout lay=mErrorView.findViewById(R.id.layout);
        //    LinearLayout lay1=mErrorView.findViewById(R.id.layout1);
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (networkInfo != null && networkInfo.isAvailable()) {
                    webView.reload();
                }
            }
        });
//        lay.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                webView.reload();
//            }
//        });
//        lay1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                webView.reload();
//            }
//        });
        //   mErrorView.setOnClickListener(null);
    }
/**
 * 加载api，根据uuid获取deviceid
 * */

    private void sendRequestWithOkHttp() {

        if (uuid==null) {
            try {
                imeiimsi();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }else {

            try {
                Log.e("auuid",uuid);

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        // 指定访问的服务器地址是电脑本机
                        .url(getjsonarr[position]+"00000000-6ab2-a592-43f6-14dd0033c587")
                      //  .url(getjsonarr[position]+uuid)
                         //  .url("http://www2.boohersmart.com:3000/api/admin_device/select/face/image/021807001")
                        //   .url("https://www.boohersmart.com/api/device/select/deviceId/"+uuid)
                        //  .url("https://aip.baidubce.com/oauth/2.0/token?" +
                        //     "grant_type=client_credentials" + "&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY)

                        .build();
                //  senduuid.setText("s_uuid:" + uuid);
                Response response = client.newCall(request).execute();


                BufferedSource source = response.body().source();

                InputStream inputStream = source.inputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder res = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    res.append(line);
                }


                responseData = res.toString();
//                    JSONArray jsonArray=new JSONArray(responseData);
//JSONObject jsonObject=new JSONObject(responseData);
                //   JSONObject jsonObject=jsonArray.getJSONObject(0);

                jsonObject = new JSONObject(responseData);
                get_uuid = jsonObject.getString("data");
                //  getdevice_id.setText("access_token" + get_uuid);
                Log.e("data", get_uuid);
                jsonArray = new JSONArray(get_uuid);
                if (jsonArray.length()==0){
                    jsonerror1=1000;
                }
                // JSONArray jsonArray=new JSONArray(responseData);
//for (int i=0;i<jsonArray.length();i++) {
                jsonObject1 = jsonArray.getJSONObject(0);

                Log.e("data", jsonArray.toString());
                device_id = jsonObject1.getString("device_id");
                Log.e(TAG, "get_access_token: access_token: " + device_id);
//}


                if (jsonObject1==null){
                    Log.e("d2", "d2");
                }
                if (device_id==null){
                    Log.e("d3", "d3");
                }






                //             JSONArray jsonArray = new JSONArray(responseData);
//
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        device_id=jsonObject.getString("device_id");
//                        String id = jsonObject.getString("device_id");
//                        Log.e("获取deviceid", "" + jsonObject);
//                        Log.e(TAG, "get_access_token: access_token: " + device_id);
//                        editor.putString("id", id);
//
//                        editor.commit();


            } catch (SocketTimeoutException e){
                e.printStackTrace();
                Log.e("","要死啦，超时了");
                Message message=new Message();
                message.what=404;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(MainActivity.this,"我寻思着没有对应的device_id啊",Toast.LENGTH_SHORT).show();
//        jsonerror=Log.getStackTraceString(e);
//        Log.e("js",jsonerror);

                Message message=new Message();
                message.what=567;
                handler.sendMessage(message);



            }


        }


    }


    private void devicetype() {


new Thread(new Runnable() {
    @Override
    public void run() {


            try {


                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        // 指定访问的服务器地址是电脑本机
                        .url("http://www2.boohersmart.com:3000/api/device/select/"+device_id)
                         //  .url("http://www2.boohersmart.com:3000/api/admin_device/select/face/image/021807001")
                        //   .url("https://www.boohersmart.com/api/device/select/deviceId/"+uuid)
                        //  .url("https://aip.baidubce.com/oauth/2.0/token?" +
                        //     "grant_type=client_credentials" + "&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY)

                        .build();
                //  senduuid.setText("s_uuid:" + uuid);
                Response response = client.newCall(request).execute();


                BufferedSource source = response.body().source();

                InputStream inputStream = source.inputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder res = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    res.append(line);
                }


            String    resData = res.toString();
//                    JSONArray jsonArray=new JSONArray(responseData);
//JSONObject jsonObject=new JSONObject(responseData);
                //   JSONObject jsonObject=jsonArray.getJSONObject(0);

           JSONObject     jsonObject = new JSONObject(resData);
            String    get_data = jsonObject.getString("data");
                //  getdevice_id.setText("access_token" + get_uuid);

           JSONArray     jsonArray = new JSONArray(get_data);
                if (jsonArray.length()==0){
                    jsonerror1=1000;
                }
                // JSONArray jsonArray=new JSONArray(responseData);
//for (int i=0;i<jsonArray.length();i++) {
              JSONObject          jsonObject1 = jsonArray.getJSONObject(0);


          device_type = jsonObject1.getString("type");

//}

                Message message=new Message();
                message.what=852;
                handler.sendMessage(message);

                //             JSONArray jsonArray = new JSONArray(responseData);
//
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        device_id=jsonObject.getString("device_id");
//                        String id = jsonObject.getString("device_id");
//                        Log.e("获取deviceid", "" + jsonObject);
//                        Log.e(TAG, "get_access_token: access_token: " + device_id);
//                        editor.putString("id", id);
//
//                        editor.commit();


            } catch (SocketTimeoutException e){
                e.printStackTrace();
                Log.e("","要死啦，超时了");
                Message message=new Message();
                message.what=404;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(MainActivity.this,"我寻思着没有对应的device_id啊",Toast.LENGTH_SHORT).show();
//        jsonerror=Log.getStackTraceString(e);
//        Log.e("js",jsonerror);

                Message message=new Message();
                message.what=567;
                handler.sendMessage(message);



            }
    }
}).start();

        }







    private  void  webview(){
//开启一个线程，如果device_id为null，跳转至sendRequestWithOkhttp,获取device_id

        loadbackground();
        new Thread(new Runnable() {

            public void run() {
                //    sendRequestWithOkHttp();

                if (device_id==null) {
                    sendRequestWithOkHttp();
                    Log.e("","运行了啊");

                }

//            nulldevice();



                Log.e(TAG, "dddddddddddddddddd: " + device_id);

                if (device_id!=null) {
                    Message message = new Message();
                    message.what = UP;
                    handler.sendMessage(message);
                }


//
//        if (device_id==null){
//
//          showErrorPage();
//          webView.setVisibility(View.INVISIBLE);
//
//        }

                //将数据发送回主线程

//if (device_id!=null) {
//    Message message = new Message();
//    message.what = UP;
//    handler.sendMessage(message);
//}

            }
        }).start();
        //     webView.loadUrl("http://device2.boohersmart.com/#/" + device_id);

//new Thread(new Runnable() {
//    @Override
//    public void run() {
//        if(device_id==null)
//            sendRequestWithOkHttp();
//        Log.e(TAG, "device: " + device_id);
//        xsdi.setText(device_id);
//        try {
//            webView.loadUrl("http://device2.boohersmart.com/#/" + device_id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}).start();





    }

//
//          webView.loadUrl("http://device.boohersmart.local/#/041812004");
//      webView.loadUrl("http://device2.boohersmart.com/#/041812002");
//          webView.loadUrl("http://192.168.1.188/#/"+id);
//      webView.loadUrl("http://device.boohersmart.com/#/");
//          Log.e(TAG, "777:" + id);
//
//      webView.clearCache(true);





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack();
            return true;
        }
        return  super.onKeyDown(keyCode,event);
    }


    private void exitActivity() {
        ExitApplication.getInstance().addActivity(Main1Activity.this);
    }

/**
 * ping网络
 * */

    public String Ping(String str) {
        Message message=new Message();
        message.what=741;
        handler.sendMessage(message);
        String resault = "";
        Process p;

        try {

            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 2 -w 10 " + str);
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());

            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        return resault;
    }

    private class NetPing extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            s = "";
            s = Ping("www.baidu.com");
            Log.e("ping", s);

            Message message=new Message();
            message.what=369;
            handler.sendMessage(message);

            return s;


        }
    }


/**
 *
 * 接收mqtt消息，开启相机
 * */


    class Mqttservicemessage extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {

                String mqttmsg = MQTTService.str1;
                JSONObject jsonObject = new JSONObject(mqttmsg);
                String mqttmsg1 = jsonObject.getString("cmd");
                if (mqttmsg1.equals("openCamera")) {

                    JSONObject object1 =new JSONObject();
                    object1.put("cmd","openCamera");
                    object1.put("code",0);
                    object1.put("msg","success");


                   topicid=0;
                    MQTTService.publish(object1.toString());




                       Intent intent1=new Intent(Main1Activity.this,RegisterAndRecognizeActivity.class);
                       startActivity(intent1);



//              Intent intent1=new Intent(Main1Activity.this,RegisterAndRecognizeActivity.class);
//              startActivity(intent1);
//              Log.e("","收到");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
/**
 * 判断网络连接
 * */
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable()) {
                //    webView.loadUrl("http://www.baidu.com");
                //  webView.loadUrl("http://board.boohersmart.com/#/");
                //     webView.loadUrl("http://device.boohersmart.com/#/041812005" );
                //    webView.reload();
                if (device_id != null) {
                    webview();
                }
                // webView.reload();
                Toast.makeText(Main1Activity.this, "有网络", Toast.LENGTH_LONG).show();
            } else {
                showErrorPage();
                webView.setVisibility(View.INVISIBLE);
                Toast.makeText(Main1Activity.this, "无网络", Toast.LENGTH_LONG).show();

            }
        }
    }



//*********人脸识别********
public void batchRegister(View view) {
    if (checkPermissions(NEEDED_PERMISSIONS)) {
        doRegister();
    } else {
        ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
    }
}

    private void doRegister() {
        File dir = new File(Constant.FILE_PATH);
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
                totalCount = jpgFiles.length;

                int successCount = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMaxProgress(totalCount);
                        progressDialog.show();
                      //  tvNotificationRegisterResult.setText("");
                      //  tvNotificationRegisterResult.append("开始注册,请等待\n\n");
                        Toast.makeText(Main1Activity.this,"开始注册,请等待\n\n",Toast.LENGTH_SHORT).show();
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
                    boolean success = FaceServer.getInstance().register(Main1Activity.this, nv21, bitmap.getWidth(), bitmap.getHeight(),
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
               finalSuccessCount = successCount;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
//                        tvNotificationRegisterResult.append("process finished!\ntotal count = " + totalCount + "\nsuccess count = " + finalSuccessCount + "\nfailed count = " + (totalCount - finalSuccessCount)
//                                + "\nfailed images are in directory '" + REGISTER_FAILED_DIR + "'");
//                        tvNotificationRegisterResult.append("注册完成!\n总计数 = " + totalCount + "\n成功计数= " + finalSuccessCount + "\n失败计数 = " + (totalCount - finalSuccessCount)
//                                + "\n注册失败的图像在文件夹'" + REGISTER_FAILED_DIR + "'");
                        Toast.makeText(Main1Activity.this,"注册完成!\n总计数 = " + totalCount + "\n成功计数= " + finalSuccessCount + "\n失败计数 = " + (totalCount - finalSuccessCount)
                                + "\n注册失败的图像在文件夹'" + REGISTER_FAILED_DIR + "'",Toast.LENGTH_SHORT).show();
                        regnum.setText("已注册人脸数:"+finalSuccessCount);
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
/**
 * 清空人脸库
 * */
    public void clearFaces() {
        Delete.deleteFolderFile(Constant.FILE_PATH,true);
        int faceNum = FaceServer.getInstance().getFaceNumber(this);
        if (faceNum == 0){
            Toast.makeText(this, R.string.no_face_need_to_delete, Toast.LENGTH_SHORT).show();
        }else {
//            AlertDialog dialog = new AlertDialog.Builder(this)
//                    .setTitle(R.string.notification)
//                    .setMessage(getString(R.string.confirm_delete,faceNum)  )
//                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
                            int deleteCount = FaceServer.getInstance().clearAllFaces(Main1Activity.this);

                            Toast.makeText(Main1Activity.this, deleteCount + " 个人脸信息已清空!", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .setNegativeButton(R.string.cancel,null)
//                    .create();
//            dialog.show();
        }
        //  Delete.deleteFolderFile(Constant.FILE_PATH,true);//删除人脸图片

    }


/**
 * 访问人脸图片api，获取图片网络地址
 * */

    public void init() {
        Log.e("initdeviceid",""+device_id);
        new Thread(new Runnable() {
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回的地址
                Request request = new Request.Builder()
                        .url("http://www.boohersmart.com:3000/api/admin_device/select/face/image/"+device_id).build();

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
                        list1.add(imageurl);


                    }

Log.e("021903006","021903006");

                } catch (IOException e) {


                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 下载人脸图片
     * */
    public  void downimage(){

        for ( i=0;i<list1.size();i++) {


            DownloadManager.getInstance().download((String) list1.get(i), new DownloadObserver());


        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setEvent(MessageEvent messageEvent){
      //  getdevice_id.setText(messageEvent.getMessage());
        String a=messageEvent.getMessage();
        list2.add(a);
        getdevice_id.setText(""+list2.size());
   //  Toast.makeText(Main1Activity.this,"第"+list2.size()+"张人脸图片下载完成,共"+list1.size()+"张图片",Toast.LENGTH_SHORT).show();
        if (list2.size()==list1.size()){
            Toast.makeText(Main1Activity.this,"下载完成",Toast.LENGTH_SHORT).show();
                doRegister();
        }
    }



public void onPause(){
        super.onPause();
        Log.e("m1","暂停");
}

public void onStop(){
        super.onStop();
    Log.e("m1","停止");
}
public  void  onRestart(){
        super.onRestart();
     //   list1.clear();
    Log.e("m1","重启");
}

public void onStart(){
        super.onStart();
     //   webView.reload();
    Log.e("m1","开始");

}


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        try {
//            deleteDatabase("webview.db");
//            deleteDatabase("webviewCache.db");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
//        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());
//        if(webviewCacheDir.exists()){
//            deleteFile(String.valueOf(webviewCacheDir));
//        }
//清空所有Cookie

//        CookieSyncManager.createInstance(getApplicationContext());  //Create a singleton CookieSyncManager within a context
//        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
//        cookieManager.removeAllCookie();// Removes all cookies.
//        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

//        webView.setWebChromeClient(null);
//        webView.setWebViewClient(null);
//        webView.getSettings().setJavaScriptEnabled(false);
        //   webView.clearCache(true);


        if (webView != null) {

          //  webView.stopLoading();


            webView.destroy();


            frameLayout.removeView(webView);
            webView.removeAllViews();
            webView = null;
            Log.e("zxc","zzc");

        }



//

//        if (device_id!=null) {
//            try {
//                client.disconnect();
//            } catch (MqttException e) {
//                e.printStackTrace();
//            }
//
//        }
        unregisterReceiver(networkChangeReceiver);
        unregisterReceiver(mqttservicemessage);

        device_id=null;
        i=0;
        System.out.println("onDestroy执行了");


        //****人脸识别******
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        FaceServer.getInstance().unInit();
        EventBus.getDefault().unregister(this);

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        list1.clear();



    }

}



