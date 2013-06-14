package com.packt.hadoop.hdfs.ch2.avro;

import java.util.Date;

public class WeblogRecord {
    
    private String cookie;
    private String page;
    private Date date;
    private String ip;
    
    public WeblogRecord() {
        
    }

    public WeblogRecord(String cookie, String page, Date date, String ip) {
        this.cookie = cookie;
        this.page = page;
        this.date = date;
        this.ip = ip;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
    @Override
    public String toString() {
        return cookie + "\t" + page + "\t" + date.toString() + "\t" + ip;
    }
    
}
