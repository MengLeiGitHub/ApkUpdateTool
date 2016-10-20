package com.uyin.apkupdate.notice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;

import com.uyin.apkupdate.R;
import com.uyin.apkupdate.listener.NoticeListener;

import java.io.File;
import java.io.Serializable;

/**
 * Created by admin on 2016/10/19.
 */
public class NoticeManager implements NoticeListener{
    private NotificationManager notificationManager;
    private Notification downLoadNotification;
    Activity  activity;

    public   NoticeManager (Activity activity){
        this.activity=activity;
    }



    public void  create(){
        notificationManager = (NotificationManager)activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        downLoadNotification = new Notification();
        downLoadNotification.icon = R.drawable.ic_launcher;
        downLoadNotification.tickerText = "开始更新";
        downLoadNotification.flags = Notification.DEFAULT_SOUND;
        downLoadNotification.flags = Notification.FLAG_SHOW_LIGHTS;
        downLoadNotification.flags = Notification.FLAG_NO_CLEAR;
        downLoadNotification.contentView = new RemoteViews(activity.getPackageName(), R.layout.view_notification_updateversion);
        notificationManager.notify(0, downLoadNotification);
    }

    @Override
    public void start() {
        create();
    }

    public  void  progress(int progress){
        downLoadNotification.contentView.setTextViewText(R.id.tv_progress, progress + "%");
        downLoadNotification.contentView.setProgressBar(R.id.pb_progress, 100, progress, false);
        notificationManager.notify(0, downLoadNotification);
    }

    @Override
    public void currentDown(long current, long total) {

    }

    public void  finish(){
        notificationManager.cancel(0);

    }

    @Override
    public void success(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }


}
