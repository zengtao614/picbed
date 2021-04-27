package com.example.demo3.test.dao;

import java.util.List;

public class BlogUser {
    private Integer id;

    private String weiboId;

    private String screenName;

    private String profileUrl;

    private String verifiedReason;

    private String description;

    private String image;

    private String containerid;

    private List<PicInstance> picInstances;

    public List<PicInstance> getPicInstances() {
        return picInstances;
    }

    public void setPicInstances(List<PicInstance> picInstances) {
        this.picInstances = picInstances;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId == null ? null : weiboId.trim();
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName == null ? null : screenName.trim();
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl == null ? null : profileUrl.trim();
    }

    public String getVerifiedReason() {
        return verifiedReason;
    }

    public void setVerifiedReason(String verifiedReason) {
        this.verifiedReason = verifiedReason == null ? null : verifiedReason.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getContainerid() {
        return containerid;
    }

    public void setContainerid(String containerid) {
        this.containerid = containerid == null ? null : containerid.trim();
    }
}