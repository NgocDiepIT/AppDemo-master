package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class LikeStatusSendForm {
    @SerializedName("userId")
    private String userId;

    @SerializedName("postId")
    private String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public LikeStatusSendForm(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
