# ApkUpdateTool
##ApkUpdateTool
 
 android 應用版本更新框架，將所有的版本更新業務進行了進一步的封裝，開發人員衹需簡單的接入就可以進行版本更新了
，支持自定義的dialog提示和自定義 notification

##使用方法

#### 如何接入？
 gradle 接入方式
 ```java
    compile(group: 'com.ml.apkupadte', name: 'appupdate', version: '1.0.3', ext: 'aar', classifier: '')
 
 ```



####調用

首先在清單文件中加入服務，以及網絡權限和文件讀寫權限
```java
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    
    
        <service android:name="com.uyin.apkupdate.service.UpdateVersionService"/>

```



 ```java
    ApkUpdateCheck<ResonseEnty ,VersionBean> apkUpdateCheck=new ApkUpdateCheck(url, new AppVersionCheckCallBack<ResonseEnty,VersionBean>() {
                    @Override
                    public String getTitle(VersionBean t) {
                                        //這個是在顯示dialog的title部分

                        return t.getVersionNo();
                    }

                    @Override
                    public String getContent(VersionBean t) {
                    //這個是在顯示dialog的cotent部分
                        return t.getVersionName();
                    }
 
                    @Override
                    public String getDownURL(VersionBean t) {
                        String url=UrlEncodeUtils.encodeUrl(t.getVersionUrl());
                        Log.e("tag","url="+url);
                        return "http://www.jiujiumiandan.cn/jiujiumiandan-user.apk";
                    }

                    @Override
                    public boolean isMustUpdate(VersionBean t) {
                            //表示是否必須更新 版本，如果返回true  dialog消失的話，app也會關閉
                        return true;
                    }

                    @Override
                    public boolean isShowDialog(ResonseEnty t1) {
                    //這個方法主要是 判斷 是否要需要更新版本，返回的boolean類型是判斷是否要顯示更新版本的dialog的
                        return true;
                    }

                    @Override
                    public String getResultDesConstants(String result) {
                         String dasd=DesConstants.DecryptDoNet(result, DesConstants.AESKey);
                        Log.e("tag",dasd);
                        dasd=  "{\"Status\":1,\"ErrCode\":\"000000\",\"ErrMsg\":null,\"ResultJson\":\"{\\\"VersionId\\\":1,\\\"VersionNo\\\":\\\"2.1.1\\\",\\\"VersionName\\\":\\\"2.1.1\\\",\\\"VersionLog\\\":\\\"久久免单---用我买单，好酒免单\\\\n\\\\n【优化】我要买单的界面布局局部调整。\\\\n\\\\n我们始终致力于改善您的体验，若果您觉得我们这个版本还不错，请在应用商城给我们一个评价，我们感激不尽。\\\\n如果您有什么问题，可以直接在微信公众号：久久免单 或 APP个人中心--反馈中直接留言，我们将及时反馈您的问题。\\\",\\\"VersionUrl\\\":\\\"http://www.jiujiumiandan.cn/jiujiumiandan-user.apk\\\",\\\"MustUpdate\\\":false}\"}";
                       
                       //dasd這裏對應的就是  ResonseEnty  ，其中的ResultJson對應的就是 VersionBean  ,所以如果服務器返回的數據類型衹是ResultJson那在初始化的時候，直接傳入兩個相同的ApkUpdateCheck<VersionBean ,VersionBean>
                       return dasd;
                    }

                    @Override
                    public   VersionBean getResultBaen(ResonseEnty  t1) {

                         return new Gson().fromJson(t1.getResultJson(),VersionBean.class);
                         //這裏是根據 ResonseEnty的返回值 初始化 VersionBean
                    }
                });
                apkUpdateCheck.setActivity(MainActivity.this);
                apkUpdateCheck.setResultBean(ResonseEnty.class);
                apkUpdateCheck.check();
 
 ```
 
* 
 #####注意 ：實體類型的字段必須要和網絡請求返回的字段一致，否則會出錯 
 
#### 如何自定義dialog和 notification？

如果要自定義dialog，衹需要繼承ApkUpdateDialog

```java
public class DefaultDialog extends ApkUpdateDialog {
    public DefaultDialog(Context context) {
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


```


和dialog不用的是，notification不但可以繼承ApkUpdateNotification來實現，你也可以通過實現NoticeListener來自己創建更炫的notification
```java
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



```
使用自定義的dialog和 Notification

```java

  ApkUpdateCheck<ResonseEnty ,VersionBean> apkUpdateCheck=new ApkUpdateCheck(url, new AppVersionCheckCallBack<ResonseEnty,VersionBean>() {
                 
                },new DefaultDialog(MainActivity.this),new DefaultNotification(MainActivity.this.getApplcation()));
                apkUpdateCheck.setActivity(MainActivity.this);
                apkUpdateCheck.setResultBean(ResonseEnty.class);
                apkUpdateCheck.check();

```




##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件:menglei0207@sina.cn
* QQ:1634990276
* github:https://github.com/MengLeiGitHub/)

##作者其他开源

* [AsyncHttp](https://github.com/MengLeiGitHub/AsyncHttp)
* [miniorm-for-android](https://github.com/MengLeiGitHub/miniOrm-for-android) 
* [AsyncPool](https://github.com/MengLeiGitHub/AsyncPool)
