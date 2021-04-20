package com.example.demo3.test.dao;

import java.util.Date;

public class CrawlerLog {
    private Integer id;

    private Integer craSource;

    private Integer craType;

    private String craParam;

    private Date craDate;

    private Integer craIsalive;

    private String craCreater;

    private String craInfo;

    private Integer craThreadnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCraSource() {
        return craSource;
    }

    public void setCraSource(Integer craSource) {
        this.craSource = craSource;
    }

    public Integer getCraType() {
        return craType;
    }

    public void setCraType(Integer craType) {
        this.craType = craType;
    }

    public String getCraParam() {
        return craParam;
    }

    public void setCraParam(String craParam) {
        this.craParam = craParam == null ? null : craParam.trim();
    }

    public Date getCraDate() {
        return craDate;
    }

    public void setCraDate(Date craDate) {
        this.craDate = craDate;
    }

    public Integer getCraIsalive() {
        return craIsalive;
    }

    public void setCraIsalive(Integer craIsalive) {
        this.craIsalive = craIsalive;
    }

    public String getCraCreater() {
        return craCreater;
    }

    public void setCraCreater(String craCreater) {
        this.craCreater = craCreater == null ? null : craCreater.trim();
    }

    public String getCraInfo() {
        return craInfo;
    }

    public void setCraInfo(String craInfo) {
        this.craInfo = craInfo == null ? null : craInfo.trim();
    }

    public Integer getCraThreadnum() {
        return craThreadnum;
    }

    public void setCraThreadnum(Integer craThreadnum) {
        this.craThreadnum = craThreadnum;
    }
}