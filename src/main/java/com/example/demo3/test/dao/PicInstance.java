package com.example.demo3.test.dao;

import java.util.Date;

public class PicInstance {
    private String id;

    private String picName;

    private String picUrl;

    private Integer picTypecode;

    private Integer picSource;

    private String picOriurl;

    private Date picSavedate;

    private Integer picHasdown;

    public Integer getPicHasdown() {
        return picHasdown;
    }

    public void setPicHasdown(Integer picHasdown) {
        this.picHasdown = picHasdown;
    }

    public String getPicOriurl() {
        return picOriurl;
    }

    public void setPicOriurl(String picOriurl) {
        this.picOriurl = picOriurl;
    }

    public Date getPicSavedate() {
        return picSavedate;
    }

    public void setPicSavedate(Date picSavedate) {
        this.picSavedate = picSavedate;
    }

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