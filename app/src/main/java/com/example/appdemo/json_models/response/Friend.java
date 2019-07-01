package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class Friend {
    @SerializedName("username")
    private String username;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("isYou")
    private boolean isYou;

    public Friend(String username, String avatarUrl, String fullName, boolean isYou) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
        this.isYou = isYou;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isYou() {
        return isYou;
    }

    public void setYou(boolean you) {
        isYou = you;
    }
}
