package com.uyin.apkupdate.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uyin.apkupdate.listener.OKListener;
import com.uyin.apkupdate.R;

/**
 * Created by admin on 2016/10/19.
 */
public abstract class ApkUpdateDialog  extends Dialog implements View.OnClickListener{


    OKListener okListener;
    boolean    isMustUpdate;
    Activity   activity;


    public void setOkListener(OKListener okListener) {
        this.okListener = okListener;
    }

    public void setIsMustUpdate(boolean isMustUpdate) {
        this.isMustUpdate = isMustUpdate;
    }

    public ApkUpdateDialog(Context context) {
        super(context, R.style.appUpdateStyle);
        activity= (Activity) context;

        DisplayMetrics  dm  =getContext().getResources().getDisplayMetrics();
        int widthPixels  =dm.widthPixels;

        View  view=getLayoutInflater().inflate(getLayout(),null);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(0,0);
        layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width= (int) (widthPixels*0.9);
        addContentView(view, layoutParams);

        this.findViewById(getConfirmViewId()).setOnClickListener(this);
        this.findViewById(getCancleViewId()).setOnClickListener(this);
    }

    public abstract  int  getLayout();
    public abstract  int  getCancleViewId();
    public abstract  int  getConfirmViewId();

    public abstract  int  getTitleViewId();
    public abstract  int  getContentViewId();




    @Override
    public void dismiss() {
        super.dismiss();
        if(isMustUpdate)activity.finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == getCancleViewId()) {
             dismiss();

        } else if (i == getConfirmViewId()) {
            okListener.OK();
        }
    }

    public void setDialogTitle(String title) {
        TextView  textView= (TextView) findViewById(getTitleViewId());
        textView.setText(title);
    }

    public void setContentText(String content) {
        TextView  textView= (TextView) findViewById(getContentViewId());
        textView.setText(content);
    }
}
