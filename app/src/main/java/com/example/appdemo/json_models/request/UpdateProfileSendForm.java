package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileSendForm {
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("address")
    private String address;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("phone")
    private String phone;

    public UpdateProfileSendForm(String fullName, String address, String birthday, String phone) {
        this.fullName = fullName;
        this.address = address;
        this.birthday = birthday;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
