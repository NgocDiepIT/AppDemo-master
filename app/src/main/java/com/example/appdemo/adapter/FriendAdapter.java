package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.json_models.response.Friend;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    ArrayList<Friend> friendList;

    public FriendAdapter(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public FriendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_friend, parent    , false);
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
        TextView tvMine;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            context = itemView.getContext();
            imvAvatar = itemView.findViewById(R.id.imv_avatar);
            tvFullName = itemView.findViewById(R.id.tv_full_name);
            tvMine = itemView.findViewById(R.id.tv_mine);

        }

        private void bindView(Friend friend) {

            Glide.with(context).load(friend.getAvatarUrl()).into(imvAvatar);
            tvFullName.setText(friend.getFullName());
            if (friend.isYou()){
                tvMine.setVisibility(View.VISIBLE);
            } else {

                tvMine.setVisibility(View.GONE);
            }
        }
    }
}
