package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateGroupChatSendForm {
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("users")
    private String[] users;

    public CreateGroupChatSendForm(String groupName, String[] users) {
        this.groupName = groupName;
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
