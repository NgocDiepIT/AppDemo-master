package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.response.Message;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    ArrayList<Message> messageArrayList;
    UserInfor userInfor;

    public MessageAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
        userInfor = RealmContext.getInstance().getUser();
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        holder.bindView(messageArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ViewFlipper viewFlipper;
        CircleImageView ivAvaFriend, ivAvaMe;
        TextView tvMessFriend, tvMessMe;
        final int MODE_FRIEND = 0;
        final int MODE_ME = 1;
        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
        }

        private void init(View itemView){
            ivAvaFriend = itemView.findViewById(R.id.iv_ava_friend);
            ivAvaMe = itemView.findViewById(R.id.iv_ava_me);
            tvMessFriend = itemView.findViewById(R.id.tv_mess_friend);
            tvMessMe = itemView.findViewById(R.id.tv_mess_me);
            viewFlipper = itemView.findViewById(R.id.view_flipper);
        }

        private void bindView(Message message){
            if(!message.getUserId().equals(userInfor.getUserId())){
                viewFlipper.setDisplayedChild(MODE_FRIEND);
                Glide.with(context).load(message.getAvatarUrl()).into(ivAvaFriend);
                tvMessFriend.setText(message.getContent());
            } else {
                viewFlipper.setDisplayedChild(MODE_ME);
                Glide.with(context).load(message.getAvatarUrl()).into(ivAvaMe);
                tvMessMe.setText(message.getContent());
            }
        }
    }
}
