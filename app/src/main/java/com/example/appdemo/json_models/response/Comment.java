package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("userAvatar")
    private String userAvatar;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("content")
    private String content;

    public Comment(String userAvatar, String fullName, String content) {
        this.userAvatar = userAvatar;
        this.fullName = fullName;
        this.content = content;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
