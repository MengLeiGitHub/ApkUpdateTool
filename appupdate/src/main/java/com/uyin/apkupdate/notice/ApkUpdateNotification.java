package com.uyin.apkupdate.notice;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.uyin.apkupdate.R;
import com.uyin.apkupdate.listener.NoticeListener;

import java.io.File;

/**
 * Created by admin on 2016/10/22.
 */

public  abstract class ApkUpdateNotification implements NoticeListener {

    private NotificationManager notificationManager;
    private Notification downLoadNotification;
    Application application;


    public ApkUpdateNotification(Application application){
        this.application=application;
    }



    public void  create(){
        notificationManager = (NotificationManager)application.getSystemService(Context.NOTIFICATION_SERVICE);
        downLoadNotification = new Notification();
        downLoadNotification.flags = Notification.DEFAULT_SOUND;
        downLoadNotification.flags = Notification.FLAG_SHOW_LIGHTS;
        downLoadNotification.flags = Notification.FLAG_NO_CLEAR;
        downLoadNotification.icon =  getLogoId();
        downLoadNotification.tickerText = "开始更新";


          downLoadNotification= initNotification( downLoadNotification);

        downLoadNotification.contentView = new RemoteViews(application.getPackageName(),getLayoutId());

      //  R.layout.view_notification_updateversion
        notificationManager.notify(0, downLoadNotification);
    }




    @Override
    public void start() {
        create();
    }

    public  void  progress(int progress){

    }

    @Override
    public void currentDown(long current, long total) {
        updateRoteView(downLoadNotification.contentView,current,total);
        notificationManager.notify(0, downLoadNotification);
    }

    public void  finish(){
         // notificationManager.cancel(0);
        onFinish();
    }

    protected abstract void onFinish();


    @Override
    public void success(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        application.startActivity(intent);
    }

    protected abstract Notification initNotification(Notification downLoadNotification);

    public abstract int getLayoutId() ;

   public abstract  void   updateRoteView(RemoteViews remoteView,long current,long total);


    public abstract int getLogoId() ;
}
