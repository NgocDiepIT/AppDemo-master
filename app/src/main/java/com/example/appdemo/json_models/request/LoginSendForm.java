package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class LoginSendForm {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String passWord;

    public LoginSendForm(String username, String passWord) {
        this.username = username;
        this.passWord = passWord;
    }

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
}
