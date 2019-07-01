package com.example.appdemo.interf;

import com.example.appdemo.json_models.response.Status;

public interface OnItemStatusClickListener {

    void onLikeClick(Status status);

    void onCommentClick(Status status);

    void onEditStatus(Status status);

    void onDeleteStatus(Status status);
}
