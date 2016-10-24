package com.uyin.apkupdate;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.uyin.apkupdate.defaults.DefaultNotification;
import com.uyin.apkupdate.listener.NoticeListener;
import com.uyin.apkupdate.notice.NoticeManager;
import com.uyin.apkupdate.service.UpdateVersionService;
import com.uyin.apkupdate.utils.StorageUtils;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by admin on 2016/10/19.
 */
public class ApkDown    {
    Application application;
    String url;
    NoticeListener noticeListener;
    MyBaodcast  baodcast=new MyBaodcast();




    protected   ApkDown(String url,Application application){
        this.url=url;
        this.application=application;

    }

    protected   ApkDown(String url,Application application,NoticeListener noticeListener){
        this.url=url;
        this.application=application;
        this.noticeListener=noticeListener;
    }

    public  void  startDownload(){
     deault();
     String filepath=StorageUtils.getStoragePath(application)+"/caCheApk.apk";
    //必须设置存储路径

    Intent intent=new Intent(application,UpdateVersionService.class);
    intent.putExtra("url",url);
    intent.putExtra("filepath",filepath);
    application.startService(intent);

    }

    private void deault() {
        if(noticeListener==null)noticeListener=new DefaultNotification(application);

            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction(Action_CURRENTDOWN);
            intentFilter.addAction(Action_PROGRESS);
            intentFilter.addAction(Action_FINISH);
            intentFilter.addAction(Action_START);
            application.registerReceiver(baodcast,intentFilter);

     }
    public static final  String    Action_PROGRESS="ACTIONPROGRESS";
    public static final  String    Action_START="ACTIONSTART";
    public static final  String    Action_FINISH="ACTIONFINISH";
    public static final  String    Action_CURRENTDOWN="ACTIONCURRENTDOWN";




    private void jiebang(){
        if(baodcast!=null){
            application.unregisterReceiver(baodcast);
            baodcast=null;
        }
    }

    private class  MyBaodcast  extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

          String acti=  intent.getAction();
            if(acti.equals(Action_PROGRESS)){
                int pros=intent.getIntExtra(Action_PROGRESS,0);
                noticeListener.progress(pros);
            }else if(acti.equals(Action_START)){
                 noticeListener.start();
            }else if(acti.equals(Action_FINISH)){
                noticeListener.finish();
                jiebang();
            }else if(acti.equals(Action_CURRENTDOWN)){
                long current=intent.getLongExtra("current", 0);
                long total=intent.getLongExtra("total", 0);
                noticeListener.currentDown(current,total);
            }
        }
    }





}
