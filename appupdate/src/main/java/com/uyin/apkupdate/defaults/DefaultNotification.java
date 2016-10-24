package com.uyin.apkupdate.defaults;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.uyin.apkupdate.R;
import com.uyin.apkupdate.listener.NoticeListener;
import com.uyin.apkupdate.notice.ApkUpdateNotification;

import java.io.File;

/**
 * Created by admin on 2016/10/22.
 */

public    class DefaultNotification extends ApkUpdateNotification {


    public DefaultNotification(Application application) {
        super(application);
    }



    @Override
    protected Notification initNotification(Notification downLoadNotification) {

        return  downLoadNotification;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_notification_updateversion;
    }

    @Override
    public void updateRoteView(RemoteViews remoteView, long current, long total) {
        int  progress= (int) (current*100f/total);
        remoteView.setTextViewText(R.id.tv_progress,""+progress+"%");
        remoteView.setProgressBar(R.id.pb_progress,100,progress,false);
    }

    @Override
    public int getLogoId() {
        return  R.drawable.ic_launcher;
    }

    @Override
    protected void onFinish() {

    }
}
