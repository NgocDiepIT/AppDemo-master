package com.example.appdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.activity.ProfileFriendActivity;
import com.example.appdemo.interf.OnItemFriendClickListener;
import com.example.appdemo.json_models.response.Friend;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    ArrayList<UserInfor> friendList;
    private OnItemFriendClickListener listener;

    public FriendAdapter(ArrayList<UserInfor> friendList, OnItemFriendClickListener listener) {
        this.friendList = friendList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_friend, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.MyViewHolder holder, int position) {
        holder.bindView(friendList.get(position));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Context context;
        CircleImageView imvAvatar;
        TextView tvFullName;
        LinearLayout itemFriend;
        UserInfor userInfor;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            context = itemView.getContext();
            imvAvatar = itemView.findViewById(R.id.imv_avatar);
            tvFullName = itemView.findViewById(R.id.tv_full_name);
            itemFriend = itemView.findViewById(R.id.item_friend);
            itemFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.viewProfileFriend(userInfor);
                }
            });
        }

        private void bindView(UserInfor userInfor) {
            this.userInfor = userInfor;
            Glide.with(context).load(userInfor.getAvatar()).into(imvAvatar);
            tvFullName.setText(userInfor.getFullName());
        }
    }
}
