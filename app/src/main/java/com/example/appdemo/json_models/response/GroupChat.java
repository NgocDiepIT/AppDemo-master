package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class GroupChat {
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("lastMessage")
    private String lastMessage;
    @SerializedName("avatars")
    private String[] avatars;

    public GroupChat(String groupId, String groupName, String lastMessage, String[] avatars) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.lastMessage = lastMessage;
        this.avatars = avatars;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String[] getAvatars() {
        return avatars;
    }

    public void setAvatars(String[] avatars) {
        this.avatars = avatars;
    }
}
