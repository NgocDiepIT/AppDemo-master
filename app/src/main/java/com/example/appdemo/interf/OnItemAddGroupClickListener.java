package com.example.appdemo.interf;

import com.example.appdemo.json_models.response.UserInfor;

import java.util.List;

public interface OnItemAddGroupClickListener {
    void createGroupChat(List<UserInfor> userInforList);
}
