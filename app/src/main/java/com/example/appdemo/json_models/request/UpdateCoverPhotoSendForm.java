package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateCoverPhotoSendForm {
    @SerializedName("coverPhoto")
    private List<String> coverPhoto;

    public UpdateCoverPhotoSendForm(List<String> coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<String> getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(List<String> coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
