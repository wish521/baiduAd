package com.baidu.baiduapplication;

public class ResponseData {
    private long timestamp;
    private String errno;
    private String errmsg;
    private Item[] data;

    public long getTimestamp() {
        return timestamp;
    }

    public String getErrno() {
        return errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public Item[] getData() {
        return data;
    }
}
