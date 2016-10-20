package com.uyin.apkupdate.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.uyin.apkupdate.R;
import com.uyin.apkupdate.bean.ProGreBean;
import com.uyin.apkupdate.listener.NoticeListener;
import com.uyin.apkupdate.utils.StorageUtils;


public class UpdateVersionService extends Service implements Runnable {
	NoticeListener  noticeListener;
	String path;
	private String downLoadUrl;
	public static final int FLAG_PROGRESS = 0, FLAG_FINISHED = 1,FLAG_START=2;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG_PROGRESS:
				noticeListener.progress(msg.arg1);
				ProGreBean proGreBean= (ProGreBean) msg.obj;
				noticeListener.currentDown(proGreBean.getCurrent(),proGreBean.getTotal());
				break;
			case FLAG_FINISHED:
 				 openFile(new File(path));
				break;
			case FLAG_START:
				if(noticeListener!=null){
					noticeListener.start();
				}
				break;
			default:
				break;
			}
		}

	};

	@Override
	public void onStart(Intent intent, int startId) {
		if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey("url")) {
			downLoadUrl = intent.getExtras().getString("url");
			if (TextUtils.isEmpty(downLoadUrl)) {
				Looper.prepare();
				Toast.makeText(getApplicationContext(), "下载地址不能为空", Toast.LENGTH_SHORT).show();
				Looper.loop();
				return;
			}
			noticeListener= (NoticeListener) intent.getExtras().getSerializable("noticeListener");
			path=intent.getExtras().getString("filepath");

			new Thread(this).start();
		} else {
			Looper.prepare();
			Toast.makeText(getApplicationContext(), "下载地址出错", Toast.LENGTH_SHORT).show();
			Looper.loop();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void run() {
		try {
			handler.sendEmptyMessage(FLAG_START);


			URL url = new URL(downLoadUrl);
 			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Charser", "GBK,utf-8;q=0.7,*;q=0.3");
			conn.setRequestProperty("Referer", url.toString());
			InputStream inputStream = conn.getInputStream();

			File file = new File(path);
			long length = conn.getContentLength();
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			long downLoadSize = 0;
			int currentSize;
			long lastUpdateTime = System.currentTimeMillis();
			while ((currentSize = inputStream.read(buffer)) > 0) {
				downLoadSize += currentSize;
				long currentUpdateTime = System.currentTimeMillis();
				if (currentUpdateTime - lastUpdateTime > 100) {
					Message msg = new Message();
					msg.what = FLAG_PROGRESS;
					msg.arg1 = (int) (downLoadSize*100f/length);
					msg.obj=new ProGreBean(downLoadSize,length);
					handler.sendMessage(msg);
					lastUpdateTime = currentUpdateTime;
				}
				outputStream.write(buffer, 0, currentSize);
			}

			outputStream.flush();
			inputStream.close();
			outputStream.close();
			conn.disconnect();
			handler.sendEmptyMessage(FLAG_FINISHED);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

    private void openFile(File file) { 
                    // TODO Auto-generated method stub 
                    Intent intent = new Intent(); 
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), 
                                    "application/vnd.android.package-archive"); 
                    startActivity(intent); 
            }

}
