package com.uyin.apkupdate.bean;

/**
 * Created by admin on 2016/10/20.
 */
public class ProGreBean {
  private   long  current;
  private long  total;

    public ProGreBean(long current,long total){
        this.current=current;
        this.total=total;
    }
    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
