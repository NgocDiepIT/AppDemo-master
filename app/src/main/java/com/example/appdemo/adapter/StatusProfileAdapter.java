package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.interf.OnItemStatusClickListener;
import com.example.appdemo.json_models.response.Status;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusProfileAdapter extends RecyclerView.Adapter<StatusProfileAdapter.MyViewHolder> {
    ArrayList<Status> statusArrayList;
    OnItemStatusClickListener listener;

    public StatusProfileAdapter(ArrayList<Status> statusArrayList, OnItemStatusClickListener listener) {
        this.statusArrayList = statusArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StatusProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_status, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusProfileAdapter.MyViewHolder holder, int position) {
        holder.bindView(statusArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return statusArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Status status;
        Context context;
        CircleImageView imageView;
        TextView tvFullName;
        TextView tvCreateDate;
        TextView tvContent;
        TextView tvNumberLike;
        TextView tvNumberCmt;
        ImageView imvLike;
        TextView tvLike;
        LinearLayout itemLike, itemComment;
        TextView tvMenu;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
            addListener();
        }


        private void init(View itemView) {
            imageView = itemView.findViewById(R.id.iv_ava);
            tvFullName = itemView.findViewById(R.id.tv_username);
            tvCreateDate = itemView.findViewById(R.id.tv_datetime);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvNumberLike = itemView.findViewById(R.id.tv_numberLike);
            tvNumberCmt = itemView.findViewById(R.id.tv_countComment);
            imvLike = itemView.findViewById(R.id.iv_like);
            tvLike = itemView.findViewById(R.id.tv_like);
            itemLike = itemView.findViewById(R.id.itemLike);
            tvMenu = itemView.findViewById(R.id.tv_menu);
            itemComment = itemView.findViewById(R.id.itemComment);
        }

        private void addListener() {
            itemLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLikeClick(status);
                    if (status.isLike()) {
                        imvLike.setBackground(context.getResources().getDrawable(R.drawable.icon_heart_black));
                        tvNumberLike.setText(String.valueOf(Integer.parseInt(tvNumberLike.getText().toString()) - 1));
                        tvLike.setTextColor(context.getResources().getColor(R.color.black));
//                        status.setNumberLike(status.getNumberLike()-1);
//                        status.setLike(false);
                    } else {
                        imvLike.setBackground(context.getResources().getDrawable(R.drawable.icon_heart_red));
                        tvNumberLike.setText(String.valueOf(Integer.parseInt(tvNumberLike.getText().toString()) + 1));
                        tvLike.setTextColor(context.getResources().getColor(R.color.red));
//                        status.setNumberLike(status.getNumberLike()+1);
//                        status.setLike(true);
                    }
                }

            });

            itemComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCommentClick(status);
                }
            });

            tvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, tvMenu);
                    popupMenu.inflate(R.menu.status_option_menu);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.option_edit:
                                    listener.onEditStatus(status);
                                    break;
                                case R.id.option_delete:
                                    listener.onDeleteStatus(status);
                                    break;
                            }
                            return false;
                        }
                    });
                }
            });
        }

        private void bindView(Status status) {
            this.status = status;

            Glide.with(context).load(status.getAuthorAvatar()).into(imageView);

            tvFullName.setText(status.getAuthorName());
            tvCreateDate.setText(status.getCreateDate());
            tvContent.setText(status.getContent());
            tvNumberLike.setText(String.valueOf(status.getNumberLike()));
            tvNumberCmt.setText(String.format("%s", status.getNumberComment()));

//            if (!status.getAuthor().equals(user.getUsername())) {
//                tv_menu.setVisibility(View.INVISIBLE);
//            } else {
//                tv_menu.setVisibility(View.VISIBLE);
//            }

            if (status.isLike()) {
                imvLike.setBackground(context.getResources().getDrawable(R.drawable.icon_heart_red));
                tvLike.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                imvLike.setBackground(context.getResources().getDrawable(R.drawable.icon_heart_black));
                tvLike.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }
}
