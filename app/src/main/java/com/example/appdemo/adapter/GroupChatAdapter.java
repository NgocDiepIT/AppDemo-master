package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemGroupChatClickListener;
import com.example.appdemo.json_models.response.GroupChat;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.MyViewHolder> {
    ArrayList<GroupChat> groupChatArrayList;
    private OnItemGroupChatClickListener listener;

    public GroupChatAdapter(ArrayList<GroupChat> groupChatArrayList, OnItemGroupChatClickListener listener) {
        this.groupChatArrayList = groupChatArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_groupchat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupChatAdapter.MyViewHolder holder, int position) {
        holder.bindView(groupChatArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return groupChatArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ViewFlipper viewFlipper;
        CircleImageView ivAvaOne, ivAvaMore1, ivAvaMore2;
        TextView tvNameGroup;
        Context context;
        RelativeLayout relativeLayout;
        final int MODE_ONE = 0;
        final int MODE_MORE = 1;
        GroupChat groupChat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
            addListener();
        }

        private void init(View itemView) {
            viewFlipper = itemView.findViewById(R.id.view_flipper);
            ivAvaOne = itemView.findViewById(R.id.iv_ava_one);
            ivAvaMore1 = itemView.findViewById(R.id.iv_ava_more1);
            ivAvaMore2 = itemView.findViewById(R.id.iv_ava_more2);
            tvNameGroup = itemView.findViewById(R.id.tv_namegroup);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }

        private void bindView(GroupChat groupChat) {
            this.groupChat = groupChat;
            String[] avas = groupChat.getAvatars();

            if (avas == null || avas.length == 0) {
                viewFlipper.setDisplayedChild(MODE_ONE);
                Glide.with(context).load(R.drawable.backgr).into(ivAvaOne);
            } else if (avas.length == 1) {
                viewFlipper.setDisplayedChild(MODE_ONE);
                Glide.with(context).load(groupChat.getAvatars()[0]).into(ivAvaOne);
            } else {
                viewFlipper.setDisplayedChild(MODE_MORE);
                Glide.with(context).load(avas[0]).into(ivAvaMore1);
                Glide.with(context).load(avas[1]).into(ivAvaMore2);
            }

            tvNameGroup.setText(groupChat.getGroupName());
        }


        private void addListener() {
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.viewConservationByGroup(groupChat);
                }
            });
        }
    }
}

