package com.arcsoft.arcfacedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.adapter.DownloadAdapter;
import com.arcsoft.arcfacedemo.download.DownloadInfo;

import com.arcsoft.arcfacedemo.download.DownloadManager;
import com.arcsoft.arcfacedemo.download.DownloadObserver;
import com.arcsoft.arcfacedemo.util.Delete;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import android.support.v7.widget.RecyclerView;


/**

 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MultipleActivity extends AppCompatActivity {


    public static String date;
    private TextView textView;
    public String id;
    public  static Button btn,delete;
  //  private RecyclerView recycler_view;
    private ListView listView;
    private DownloadAdapter mAdapter;
    private ArrayList<DownloadInfo> mData;
    public static List list1=new ArrayList();
private String dz;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        // EventBus.getDefault().register(this);
        // recycler_view = findViewById(R.id.recycler_view);
        btn=findViewById(R.id.btn);
        delete=findViewById(R.id.delete);




        listView = findViewById(R.id.list_view);
        textView = findViewById(R.id.txt);
        init();



//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i=0;i<list1.size();i++) {
//                    DownloadManager.getInstance().download((String) list1.get(i), new DownloadObserver());
//                }
//                //    DownloadManager.getInstance().download(Constant.URL_3, new DownloadObserver());
//                File path = new File(Constant.FILE_PATH);
//                if (path.exists()) {
//                    Toast.makeText(MultipleActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete.deleteFolderFile(Constant.FILE_PATH,true);
            }
        });

//
//        mData.add(new DownloadInfo(Constant.URL_1));
//        mData.add(new DownloadInfo(URL_2));
//        mData.add(new DownloadInfo(Constant.URL_3));
//        mData.add(new DownloadInfo(Constant.URL_4));
//        mData.add(new DownloadInfo(Constant.URL_5));
//        mData.add(new DownloadInfo(Constant.URL_6));
//        mData.add(new DownloadInfo(Constant.URL_7));
//        mData.add(new DownloadInfo(Constant.URL_8));
//        mData.add(new DownloadInfo(Constant.URL_9));

    }
     //   mAdapter = new DownloadAdapter(mData);
//        recycler_view.setLayoutManager(new LinearLayoutManager(this));
//        recycler_view.setAdapter(mAdapter);
//        // 取消item刷新的动画
//        ((SimpleItemAnimator)recycler_view.getItemAnimator()).setSupportsChangeAnimations(false);
//init();
//    }
//
//    @Subscribe (threadMode = ThreadMode.MAIN)
//    public void update(DownloadInfo info){
//        if (DownloadInfo.DOWNLOAD.equals(info.getDownloadStatus())){
//
//            mAdapter.updateProgress(info);
//
//        }else if (DownloadInfo.DOWNLOAD_OVER.equals(info.getDownloadStatus())){
//
//            mAdapter.updateProgress(info);
//
//        }//else if (DownloadInfo.DOWNLOAD_PAUSE.equals(info.getDownloadStatus())){
//
//            Toast.makeText(this,"下载暂停",Toast.LENGTH_SHORT).show();
//
//        }else if (DownloadInfo.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())){
//
//            mAdapter.updateProgress(info);
//            Toast.makeText(this,"下载取消",Toast.LENGTH_SHORT).show();
//
//        }else if (DownloadInfo.DOWNLOAD_ERROR.equals(info.getDownloadStatus())){
//
//            Toast.makeText(this,"下载出错",Toast.LENGTH_SHORT).show();
//        }
//    }

public static void downimage(){

    for (int i=0;i<list1.size();i++) {
                    DownloadManager.getInstance().download((String) list1.get(i), new DownloadObserver());
                }
                //    DownloadManager.getInstance().download(Constant.URL_3, new DownloadObserver());
                File path = new File(Constant.FILE_PATH);
                if (path.exists()) {
                  //  Toast.makeText(this,"下载完成",Toast.LENGTH_SHORT).show();
                }
}



        public static void init() {
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
    list1.add(imageurl);
}


                    } catch (IOException e) {


                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        public Handler han = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
//                        listView.setAdapter(new DownloadAdapter(MultipleActivity.this, mData));//调用listview适配器
                        break;
                    case 2:
                        listView.setAdapter(new DownloadAdapter(MultipleActivity.this, mData));//调用listview适配器
                        break;

                }
            }

        };
        private void Gsonjx(String date) { //gson解析部分

            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(date).getAsJsonArray();
            Gson gson = new Gson();
            mData = new ArrayList<>();
            for (JsonElement post : jsonArray) {
                DownloadInfo postCourse = gson.fromJson(post, DownloadInfo.class);
                mData.add(postCourse);
            }
            Message message = new Message();
            message.what = 2;
            han.sendMessage(message);
        }


        @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
