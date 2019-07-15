package com.example.appdemo.json_models.request;

import com.google.gson.annotations.SerializedName;

public class UpdateGroupNameSendForm {
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("name")
    private String name;

    public UpdateGroupNameSendForm(String groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
