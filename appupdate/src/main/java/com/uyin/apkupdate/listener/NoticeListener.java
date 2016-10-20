package com.uyin.apkupdate.listener;

import java.io.File;
import java.io.Serializable;

/**
 * Created by admin on 2016/10/20.
 */
public interface NoticeListener extends Serializable {
    public void    start();
    public void    progress(int progress);
    public void    currentDown(long   current ,long total);
    public void    finish();
    public void    success(File file);
}
