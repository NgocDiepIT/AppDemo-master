package com.example.appdemo.json_models.response;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;

public class Status{
    @SerializedName("postId")
    private String postId;

    @SerializedName("author")
    private String author;

    @SerializedName("authorName")
    private String authorName;

    @SerializedName("authorAvatar")
    private String authorAvatar;

    @SerializedName("content")
    private String content;

    @SerializedName("createDate")
    private String createDate;

    @SerializedName("numberLike")
    private int numberLike;

    @SerializedName("numberComment")
    private int numberComment;

    @SerializedName("isLike")
    private boolean isLike;

    public Status() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getNumberComment() {
        return numberComment;
    }

    public void setNumberComment(int numberComment) {
        this.numberComment = numberComment;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public Status(String postId, String author, String authorName, String authorAvatar, String content, String createDate, int numberLike, int numberComment, boolean isLike) {
        this.postId = postId;
        this.author = author;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.content = content;
        this.createDate = createDate;
        this.numberLike = numberLike;
        this.numberComment = numberComment;
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "Status{" +
                "postId='" + postId + '\'' +
                ", author='" + author + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", numberLike=" + numberLike +
                ", numberComment=" + numberComment +
                ", isLike=" + isLike +
                '}';
    }
}
