package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.interf.OnItemStatusClickListener;
import com.example.appdemo.json_models.response.Comment;
import com.example.appdemo.json_models.response.Status;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    ArrayList<Comment> commentList;
    Context context;

    public CommentAdapter(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.MyViewHolder holder, int position) {
        holder.bindView(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAva;
        TextView tvFullname;
        TextView tvComment;
        Comment comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
        }

        private void init(View itemView) {
            ivAva = itemView.findViewById(R.id.iv_ava);
            tvFullname = itemView.findViewById(R.id.tv_username);
            tvComment = itemView.findViewById(R.id.tv_content);
        }

        private void bindView(Comment comment){
            this.comment = comment;
            Glide.with(context).load(comment.getUserAvatar()).into(ivAva);
            tvFullname.setText(comment.getFullName());
            tvComment.setText(comment.getContent());
        }
    }
}
