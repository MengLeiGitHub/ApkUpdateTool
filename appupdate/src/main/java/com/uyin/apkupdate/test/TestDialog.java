package com.uyin.apkupdate.test;

import android.content.Context;

import com.uyin.apkupdate.R;
import com.uyin.apkupdate.dialog.ApkUpdateDialog;

/**
 * Created by admin on 2016/10/20.
 */
public class TestDialog  extends ApkUpdateDialog {
    public TestDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.app_update_custom_dialog;
    }

    @Override
    public int getCancleViewId() {
        return R.id.cancel;
    }

    @Override
    public int getConfirmViewId() {
        return R.id.confirm;
    }

    @Override
    public int getTitleViewId() {
        return R.id.dialog_title;
    }

    @Override
    public int getContentViewId() {
        return R.id.dialog_deatail;
    }
}
