package com.example.demo3.test.dao;

public class PicInstance {
    private String id;

    private String picName;

    private String picUrl;

    private Integer picTypecode;

    private Integer picSource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getPicTypecode() {
        return picTypecode;
    }

    public void setPicTypecode(Integer picTypecode) {
        this.picTypecode = picTypecode;
    }

    public Integer getPicSource() {
        return picSource;
    }

    public void setPicSource(Integer picSource) {
        this.picSource = picSource;
    }
}