package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class CoverPhoto {
    @SerializedName("coverPhoto")
    private String[] coverPhoto;

    public CoverPhoto(String[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
