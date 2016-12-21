# ApkUpdateTool
##ApkUpdateTool
 
 android 應用版本更新框架，將所有的版本更新業務進行了進一步的封裝，開發人員衹需簡單的接入就可以進行版本更新了
，支持自定義的dialog提示和自定義 notification

##使用方法

#### 如何接入？
 gradle 接入方式
 ```java
    compile 'com.ml.apkupadte:appupdate:1.0.7@aar'
    compile 'com.ml.asynchttp:asynchttp-android:1.1.0'

 
 ```



####調用

首先在清單文件中加入服務，以及網絡權限和文件讀寫權限
```java
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    
    
    <service android:name="com.uyin.apkupdate.service.UpdateVersionService"/>

```


####在线检测 (直接设置更新接口，以及参数)
 ```java
     ApkUpdateOnline<User> userApkUpdateOnline= new ApkUpdateOnline<User>(Activity.this, "http://120.26.106.106:8080/rest/common/user/login.do") {
                    @Override
                    public String getTitle(User user) {
                    
                        return user.getData().getUsername();
                    }

                    @Override
                    public String getContent(User user) {
                    //dialog显示content内容
                        return user.getData().getUsername();
                    }

                    @Override
                    public String getDownURL(User user) {
                    
                        return user.getData().getAvatar();
                    }

                    @Override
                    public boolean isMustUpdate(User user) {
                    //是否必须更新，如果为true 关闭dialog app则会关闭
                        return false;
                    }

                    @Override
                    public boolean isShowDialog(User t1) {
                    //是否显示dialog
                        return true;
                    }

                    @Override
                    public String getResultDesConstants(String str) {
                    //这个是对于网络数据加密的，如果数据没加密无需操作该方法
                        return null;
                    }
                };
                HashMap   map=new HashMap<>();
                map.put("username","15093201628");
                map.put("password","e565f08d058ebb4a1c99907a9860a93b");

                userApkUpdateOnline.CheckByJSONPost(map);
 
 ```
 
 ####本地检测 ，需要传入一个实体类 
 ```java
     ApkUpdateCheck apkUpdateCheck=new ApkUpdateCheck(new AppVersionCheckCallBack<User>(){

                    @Override
                    public String getTitle(User user) {
                        return null;
                    }

                    @Override
                    public String getContent(User user) {
                        return null;
                    }

                    @Override
                    public String getDownURL(User user) {
                        return user.getMsg();
                    }

                    @Override
                    public boolean isMustUpdate(User user) {
                        return false;
                    }

                    @Override
                    public boolean isShowDialog(User t1) {
                        return false;
                    }

                    @Override
                    public String getResultDesConstants(String str) {
                        return null;
                    }
                });
                User user=new User();
                user.setMsg("下载地址");
                apkUpdateCheck.setActivity(Activity activity)
                apkUpdateCheck.check(user);
 
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


*和dialog不用的是，notification不但可以繼承ApkUpdateNotification來實現，你也可以通過實現NoticeListener來自己創建更炫的notification
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
 ApkUpdateCheck(AppVersionCheckCallBack appVersionCheckCallBack,ApkUpdateDialog apkUpdateDialog,NoticeListener noticeListener)
 
 ApkUpdateOnline(Activity activity,String url, ApkUpdateDialog apkUpdateDialog, NoticeListener noticeListener)
 

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
