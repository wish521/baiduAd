package com.baidu.baiduapplication;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Item {
    @SerializedName("item_id")
    private int itemId;
    private String type;
    private String title;
    private String area;
    private String site;
    private String preload_url;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPreload_url() {
        return preload_url;
    }

    public void setPreload_url(String preload_url) {
        this.preload_url = preload_url;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getItemId() {
        return itemId;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}

