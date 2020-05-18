package com.dreanei.communion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topic {
    public static final Topic NO_TOPIC = new Topic(-2,-2,-2,"no topics","-");

    @SerializedName("topicid")
    @Expose
    private Integer topicid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("sectionid")
    @Expose
    private Integer sectionid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("update")
    @Expose
    private String update;

    public Topic(Integer topicid, Integer userid, Integer sectionid, String title, String update) {
        this.topicid = topicid;
        this.userid = userid;
        this.sectionid = sectionid;
        this.title = title;
        this.update = update;
    }

    public Integer getTopicid() {
        return topicid;
    }

    public void setTopicid(Integer topicid) {
        this.topicid = topicid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getSectionid() {
        return sectionid;
    }

    public void setSectionid(Integer sectionid) {
        this.sectionid = sectionid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return title;
    }
}