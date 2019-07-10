package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("userId")
    private String userId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("avatarUrl")
    private String avatarUrl;
    @SerializedName("content")
    private String content;

    public Message(String userId, String fullName, String avatarUrl, String content) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
