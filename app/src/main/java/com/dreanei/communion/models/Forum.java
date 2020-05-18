package com.dreanei.communion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forum {
    public static final Forum NO_FORUMS = new Forum(-1,-1,"no forums","https://upload.wikimedia.org/wikipedia/commons/5/59/Empty.png",0);
    public static final Forum WAIT = new Forum(-1,-1,"loading...","https://upload.wikimedia.org/wikipedia/commons/5/59/Empty.png",0);
    public static final Forum EMPTY = new Forum(-1,-1,"empty","https://upload.wikimedia.org/wikipedia/commons/5/59/Empty.png",0);

    @SerializedName("forumid")
    @Expose
    private Integer forumid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("public")
    @Expose
    private Integer _public;

    public Integer getForumid() {
        return forumid;
    }

    public void setForumid(Integer forumid) {
        this.forumid = forumid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublic() {
        return _public;
    }

    public void setPublic(Integer _public) {
        this._public = _public;
    }

    public Forum(int forumid, int userid, String title, String img, int _public) {
        this.forumid = forumid;
        this.userid = userid;
        this.title = title;
        this.img = img;
        this._public = _public;
    }

    public Forum(int userid, String img, String title, int _public) {
        this.forumid = -1;
        this.userid = userid;
        this.title = title;
        this.img = img;
        this._public = _public;
    }
    @Override
    public String toString() {
        return title;
    }
}