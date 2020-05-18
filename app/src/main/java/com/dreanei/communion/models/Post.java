package com.dreanei.communion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("postid")
    @Expose
    private Integer postid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("topicid")
    @Expose
    private Integer topicid;
    @SerializedName("postName")
    @Expose
    private String postName;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("postDate")
    @Expose
    private String postDate;

    public Post(Integer postid, Integer userid, Integer topicid, String postName, String post) {
        this.postid = postid;
        this.userid = userid;
        this.topicid = topicid;
        this.postName = postName;
        this.post = post;
    }
    public Post(Integer postid, Integer userid, Integer topicid, String postName, String post,String date) {
        this.postid = postid;
        this.userid = userid;
        this.topicid = topicid;
        this.postName = postName;
        this.post = post;
        postDate = date;
    }
    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTopicid() {
        return topicid;
    }

    public void setTopicid(Integer topicid) {
        this.topicid = topicid;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return postName + "\n" + post;
    }
}