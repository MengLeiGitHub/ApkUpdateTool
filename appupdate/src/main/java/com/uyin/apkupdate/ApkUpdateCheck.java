package com.uyin.apkupdate;

import android.app.Activity;

import com.async.http.AsyncHttp;
import com.async.http.callback.HttpCallBack;
import com.async.http.clientImpl.HttpMethod;
import com.async.http.constant.Charsets;
import com.async.http.entity.ResponseBody;
import com.async.http.request2.StringRequest;
import com.google.gson.Gson;
import com.uyin.apkupdate.dialog.ApkUpdateDialog;
import com.uyin.apkupdate.listener.AppVersionCheckCallBack;
import com.uyin.apkupdate.listener.NoticeListener;
import com.uyin.apkupdate.listener.OKListener;
import com.uyin.apkupdate.test.TestDialog;


/**
 * Created by admin on 2016/10/19.
 */
public    class ApkUpdateCheck<T,N>   implements HttpCallBack<ResponseBody<String>>{
    private  String   checkurl;
    AppVersionCheckCallBack appVersionCheckCallBack;
    ApkUpdateDialog   apkUpdateDialog;
    NoticeListener    noticeListener;

    private Activity activity;
    Class   t;
    N   n;
   public ApkUpdateCheck setResultBean(Class resultClass){
        this.t=resultClass;
       return this;
    }
    public  ApkUpdateCheck  setParseBean(N n){
        this.n=n;
        return this;
    }



    public ApkUpdateCheck setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }
    public ApkUpdateCheck setActivity(Activity activity,ApkUpdateDialog apkUpdateDialog) {
        this.activity = activity;
        this.apkUpdateDialog=apkUpdateDialog;
        return this;
    }


    /**
     * 自定义所有的
     * @param url
     * @param appVersionCheckCallBack
     * @param apkUpdateDialog
     * @param noticeListener
     */
    public   ApkUpdateCheck(String url,AppVersionCheckCallBack appVersionCheckCallBack,ApkUpdateDialog apkUpdateDialog,NoticeListener noticeListener){
        checkurl=url;
        this.appVersionCheckCallBack=appVersionCheckCallBack;
        this.apkUpdateDialog=apkUpdateDialog;
        this.noticeListener=noticeListener;
     }


    public   void check() {
        StringRequest resReques=new StringRequest(checkurl, Charsets.UTF_8);
        resReques.setRequestMethod(HttpMethod.Get);
        AsyncHttp.instance().stringRequest(resReques, this);
     }




    @Override
    public void start() {

    }

    @Override
    public void current(long current, long total) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void success(ResponseBody<String> result) {
        String str = result.getResult();
        String resultDesConstants=appVersionCheckCallBack.getResultDesConstants(str);
        if(resultDesConstants!=null)str=resultDesConstants;

        T  t1= (T) new Gson().fromJson(str, t);

        final   boolean  showdialog=appVersionCheckCallBack.isShowDialog(t1);

        if(showdialog){
            N   n1= (N) appVersionCheckCallBack.getResultBaen(t1);
            final   String content=appVersionCheckCallBack.getContent(n1);
            final   String title=appVersionCheckCallBack.getTitle(n1);
            final   String  downURL=appVersionCheckCallBack.getDownURL(n1);
            final   boolean  isMustUpdate=appVersionCheckCallBack.isMustUpdate(n1);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(apkUpdateDialog==null)apkUpdateDialog = new TestDialog(activity);
                    apkUpdateDialog.setDialogTitle(title);
                    apkUpdateDialog.setContentText(content);
                    apkUpdateDialog.setIsMustUpdate(isMustUpdate);
                    apkUpdateDialog.setCanceledOnTouchOutside(!isMustUpdate);
                    apkUpdateDialog.setOkListener(new OKListener() {
                        @Override
                        public void OK() {
                            new ApkDown(downURL,activity,noticeListener).startDownload();
                        }
                    });
                    apkUpdateDialog.show();
                }
            });
        }
    }

    @Override
    public void fail(Exception e, ResponseBody<String> request) {

    }
}




