package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class Avatar {
    @SerializedName("avatarUrl")
    private String avatarUrl;

    public Avatar() {
    }

    public Avatar(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
