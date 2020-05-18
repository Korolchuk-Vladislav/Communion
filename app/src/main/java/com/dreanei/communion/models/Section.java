package com.dreanei.communion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    public static final Section NO_SECTION = new Section(-1,-1,"NO SECTIONS",-1);

    @SerializedName("sectionid")
    @Expose
    private Integer sectionid;
    @SerializedName("forumid")
    @Expose
    private Integer forumid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent")
    @Expose
    private Integer parent;

    public Section(Integer sectionid, Integer forumid, String name, Integer parent) {
        this.sectionid = sectionid;
        this.forumid = forumid;
        this.name = name;
        this.parent = parent;
    }

    public Integer getSectionid() {
        return sectionid;
    }

    public void setSectionid(Integer sectionid) {
        this.sectionid = sectionid;
    }

    public Integer getForumid() {
        return forumid;
    }

    public void setForumid(Integer forumid) {
        this.forumid = forumid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return getName() + "parent: " + getParent();
    }
}