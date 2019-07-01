package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class RegisterSendForm {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String passWord;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("adress")
    private String adress;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("phone")
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public RegisterSendForm(String username, String passWord, String fullName, String adress, String birthday, String phone) {
        this.username = username;
        this.passWord = passWord;
        this.fullName = fullName;
        this.adress = adress;
        this.birthday = birthday;
        this.phone = phone;
    }
}
