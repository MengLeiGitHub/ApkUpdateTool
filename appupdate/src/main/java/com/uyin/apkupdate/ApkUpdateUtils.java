package com.uyin.apkupdate;


import com.async.http.AsyncHttp;
import com.async.http.clientImpl.HttpMethod;
import com.async.http.constant.Constents;
import com.async.http.request2.RequestConfig;
import com.async.http.request2.entity.Header;

import java.util.ArrayList;

/**
 * Created by admin on 2016/10/19.
 */
public class ApkUpdateUtils {
    public static  void init(){
        RequestConfig requestConfig=new RequestConfig();
        requestConfig.setConnectTimeout(10000);
        requestConfig.setSocketTimeout(30000);
        requestConfig.setRequestMethod(HttpMethod.Post);
        ArrayList<Header> headerlist=new ArrayList<Header>();
        headerlist.add(new Header("connection", "Keep-Alive"));
        headerlist.add(new Header("user-agent", "AsyncHttp 1.0"));
        headerlist.add(new Header("Accept", "*/*"));

        headerlist.add(new Header("Accept-Charset", "utf-8"));
        headerlist.add(new Header(Constents.CONTENT_TYPE, Constents.TYPE_FORM_DATA));
        requestConfig.setHeadList(headerlist);
        AsyncHttp.instance().setConfig(requestConfig);
    }
}
