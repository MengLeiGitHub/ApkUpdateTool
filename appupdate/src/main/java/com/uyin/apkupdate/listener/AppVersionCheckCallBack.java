package com.uyin.apkupdate.listener;

/**
 * Created by admin on 2016/10/19.
 */
public interface AppVersionCheckCallBack<T,N> {
    public   String  getTitle(N t);
    public   String  getContent(N t);
    public   String  getDownURL(N t);
    public   boolean isMustUpdate(N t);
    public   boolean isShowDialog(T t1);

    public   String   getResultDesConstants(String str);

     N getResultBaen(T t1);
}
