package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class CreateStatusSendForm {
    @SerializedName("userId")
    private String userId;

    @SerializedName("content")
    private String content;

    public CreateStatusSendForm(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
