package com.dreanei.communion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("photo_rec")
    @Expose
    private String photo_rec;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("type")
    @Expose
    private Integer type;

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getUserid() {
        return userid;
    }
    public String getHash() {
        return hash;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getPhoto() {
        return photo;
    }
    public String getPhoto_rec() {
        return photo_rec;
    }
    public int getAge() {
        return age;
    }
    public String getCountry() {
        return country;
    }
    public String getRegion() {
        return region;
    }
    public String getCity() {
        return city;
    }
    public String getAbout() {
        return about;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setPhoto_rec(String photo_rec) {
        this.photo_rec = photo_rec;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    public User(){}

    public User(int userid, String hash, String first_name, String last_name, String photo, String photo_rec,
                int age, String country, String region, String city, String about, int type) {
        this.userid = userid;
        this.hash = hash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.photo = photo;
        this.photo_rec = photo_rec;
        this.age = age;
        this.country = country;
        this.region = region;
        this.city = city;
        this.about = about;
        this.type = type;
    }

    public User(int userid, String hash, String first_name, String last_name, String photo, String photo_rec,
                int age, String country, String region, String city, String about) {
        this.userid = userid;
        this.hash = hash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.photo = photo;
        this.photo_rec = photo_rec;
        this.age = age;
        this.country = country;
        this.region = region;
        this.city = city;
        this.about = about;
    }

    public User(int userid, String hash, String first_name, String last_name, String photo, String photo_rec) {
        this.userid = userid;
        this.hash = hash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.photo = photo;
        this.photo_rec = photo_rec;
    }

    @Override
    public String toString() {
        return "User [userid=" + userid + ", " + (hash != null ? "passwordhash=" + hash + ", " : "")
                + (first_name != null ? "first_name=" + first_name + ", " : "")
                + (last_name != null ? "last_name=" + last_name + ", " : "")
                + (photo != null ? "photo=" + photo + ", " : "")
                + (photo_rec != null ? "photo_rec=" + photo_rec + ", " : "") + "age=" + age + ", "
                + (country != null ? "country=" + country + ", " : "")
                + (region != null ? "region=" + region + ", " : "") + (city != null ? "city=" + city + ", " : "")
                + (about != null ? "about=" + about : "") + "]";
    }
}
