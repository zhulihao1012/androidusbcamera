package com.arcsoft.arcfacedemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.Constant;
import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.download.DownloadInfo;
import com.arcsoft.arcfacedemo.download.DownloadManager;
import com.arcsoft.arcfacedemo.download.DownloadObserver;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DownloadAdapter extends BaseAdapter {
private TextView t1;
    private Button down,pause,cancel;
    public Context con;
    private List<DownloadInfo> mdata;
public LayoutInflater inflater;
    public DownloadAdapter(Context context, ArrayList<DownloadInfo> mdata) {
        this.con=context;
        this.mdata = mdata;
        inflater=LayoutInflater.from(con);

    }





//    public void updateProgress(DownloadInfo info){
//        for (int i = 0; i < mdata.size(); i++){
//            if (mdata.get(i).getUrl().equals(info.getUrl())){
//                mdata.set(i,info);
//                notifyItemChanged(i);
//                break;
//            }
//        }
//    }

//    @Override
//    public UploadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(parent.getContext(), R.layout.item_download_layout,null);
//        return new UploadHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(UploadHolder holder, int position) {
//
//        final DownloadInfo info = mdata.get(position);
//
//        if (DownloadInfo.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())){
//            holder.main_progress.setProgress(0);
//        }else if (DownloadInfo.DOWNLOAD_OVER.equals(info.getDownloadStatus())){
//            holder.main_progress.setProgress(holder.main_progress.getMax());
//        }else {
//            if (info.getTotal() == 0){
//                holder.main_progress.setProgress(0);
//            }else {
//                float d = info.getProgress() * holder.main_progress.getMax() / info.getTotal();
//                holder.main_progress.setProgress((int) d);
//            }
//        }
//        holder.main_btn_down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DownloadManager.getInstance().download(info.getUrl(), new DownloadObserver());
//            }
//        });
//
//        holder.main_btn_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DownloadManager.getInstance().pauseDownload(info.getUrl());
//            }
//        });
//
//        holder.main_btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DownloadManager.getInstance().cancelDownload(info);
//            }
//        });
//    }



    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_download_layout,null);
      //  final DownloadInfo info = mdata.get(position);

        down=view.findViewById(R.id.main_btn_down);
        pause=view.findViewById(R.id.main_btn_pause);
        cancel=view.findViewById(R.id.main_btn_cancel);
          t1=view.findViewById(R.id.text1);
t1.setText(mdata.get(position).getDevice_id());
Log.e("",""+position);
        Log.e("x","x"+mdata.get(position).getImage());
Log.e("时间戳","时间"+System.currentTimeMillis());


        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.getInstance().download(Constant.URL_3+mdata.get(position).getImage(), new DownloadObserver());
           //     DownloadManager.getInstance().download(Constant.URL_3, new DownloadObserver());
                File path = new File(Constant.FILE_PATH);
                if (path.exists()) {
                    Toast.makeText(con,"下载完成",Toast.LENGTH_SHORT).show();
                }
            }
        });





        return  view;
    }

//    public class UploadHolder extends RecyclerView.ViewHolder{
//
//        private ProgressBar main_progress;
//        private Button main_btn_down;
//        private Button main_btn_pause;
//        private Button main_btn_cancel;
//
//        public UploadHolder(View itemView) {
//            super(itemView);
//            main_progress = itemView.findViewById(R.id.main_progress);
//            main_btn_down = itemView.findViewById(R.id.main_btn_down);
//            main_btn_pause = itemView.findViewById(R.id.main_btn_pause);
//            main_btn_cancel = itemView.findViewById(R.id.main_btn_cancel);
//        }
//    }

}
