package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class CommentCreate {

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("username")
    private String username;

    @SerializedName("userAvatar")
    private String userAvatar;

    @SerializedName("content")
    private String content;

    public CommentCreate(String fullName, String username, String userAvatar, String content) {
        this.fullName = fullName;
        this.username = username;
        this.userAvatar = userAvatar;
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
