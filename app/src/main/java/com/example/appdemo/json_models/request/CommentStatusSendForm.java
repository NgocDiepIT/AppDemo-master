package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class CommentStatusSendForm {
    @SerializedName("userId")
    private String userId;

    @SerializedName("postId")
    private String postId;

    @SerializedName("content")
    private String content;

    public CommentStatusSendForm(String userId, String postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
