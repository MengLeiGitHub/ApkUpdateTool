package com.uyin.apkupdate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.async.http.AsyncHttp;
import com.async.http.callback.DownProgrossCallback;
import com.async.http.clientImpl.HttpMethod;
import com.async.http.entity.ResponseBody;
import com.async.http.handler.TaskHandler;
import com.async.http.request2.FileRequest;
import com.async.http.request2.download;
import com.async.http.request2.entity.Header;
import com.async.http.request2.record.RecordEntity;
import com.uyin.apkupdate.listener.NoticeListener;
import com.uyin.apkupdate.notice.NoticeManager;
import com.uyin.apkupdate.service.UpdateVersionService;
import com.uyin.apkupdate.utils.StorageUtils;

import java.io.File;
import java.io.InputStream;

/**
 * Created by admin on 2016/10/19.
 */
public class ApkDown   {
    Activity activity;
    String url;
    NoticeListener noticeListener;





    protected   ApkDown(String url,Activity activity){
        this.url=url;
        this.activity=activity;
    }

    protected   ApkDown(String url,Activity activity,NoticeListener noticeListener){
        this.url=url;
        this.activity=activity;
        this.noticeListener=noticeListener;
    }

    public  void  startDownload(){
        deault();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //必须设置存储路径
                String filepath=getStoragePath()+"/caCheApk1.apk";

              /*  download resReques=  new download(new RecordEntity(url,filepath));
                resReques.setFilepath(filepath);
                resReques.setUrl(url);
                resReques.addHead(new Header("user-agent", "AsyHttp/1.0 ml"));

                resReques.setRequestMethod(HttpMethod.Get);

                TaskHandler taskhandler= AsyncHttp.instance().download(resReques,ApkDown.this);*/
                Intent intent=new Intent(activity,UpdateVersionService.class);
                intent.putExtra("url",url);
                intent.putExtra("filepath",filepath);
                intent.putExtra("noticeListener",noticeListener);
                activity.startService(intent);


            }
        }).start();

        //可以调用 taskhandler.stop()方法取消任务

        //FileTest 是 继承了 DownProgrossCallback<ResponseBody<T>>的 回掉接口，实现进度的监控，和结果的返回

    }

    private void deault() {
        if(noticeListener==null)noticeListener=new NoticeManager(activity);

    }


    private  String  getStoragePath(){
        String path= StorageUtils.getCacheDirectory(activity).toString();
        File fileDir=new File(path);
        if(!fileDir.exists()){
            try {
                fileDir.mkdirs();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return path;

    }

}
