package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

public class GroupChatCreate {
    @SerializedName("name")
    private String name;
    @SerializedName("users")
    private String[] users;
    @SerializedName("_id")
    private String _id;
    @SerializedName("messages")
    private String[] messages;

    public GroupChatCreate(String name, String[] users, String _id, String[] messages) {
        this.name = name;
        this.users = users;
        this._id = _id;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }
}
