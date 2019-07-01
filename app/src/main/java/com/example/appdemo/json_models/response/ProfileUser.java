package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileUser {
    @SerializedName("username")
    private String username;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("avatarUrl")
    private String avatarUrl;
    @SerializedName("coverPhoto")
    private String[] coverPhoto;
    @SerializedName("postList")
    private ArrayList<Status> postList;

    public ProfileUser(String username, String fullName, String address, String phone, String birthday, String avatarUrl, String[] coverPhoto, ArrayList<Status> postList) {
        this.username = username;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.avatarUrl = avatarUrl;
        this.coverPhoto = coverPhoto;
        this.postList = postList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public ArrayList<Status> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Status> postList) {
        this.postList = postList;
    }

    @Override
    public String toString() {
        return "ProfileUser{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", coverPhoto=" + Arrays.toString(coverPhoto) +
                ", postList=" + postList +
                '}';
    }
}
