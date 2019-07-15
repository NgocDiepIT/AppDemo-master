package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class UpdateCoverPhotoSendForm {
    @SerializedName("coverPhoto")
    private String[] coverPhoto;

    public UpdateCoverPhotoSendForm(String[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
