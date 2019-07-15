package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberOfGroupAdapter extends RecyclerView.Adapter<MemberOfGroupAdapter.MyViewHolder> {
    ArrayList<UserInfor> memberLists;

    public MemberOfGroupAdapter(ArrayList<UserInfor> memberLists) {
        this.memberLists = memberLists;
    }

    @NonNull
    @Override
    public MemberOfGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_friend, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberOfGroupAdapter.MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return memberLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        Context context;
        CircleImageView imvAvatar;
        TextView tvFullName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imvAvatar = itemView.findViewById(R.id.imv_avatar);
            tvFullName = itemView.findViewById(R.id.tv_full_name);
        }

        private void bindView(int position){
            tvFullName.setText(memberLists.get(position).getFullName());
            Glide.with(context).load(memberLists.get(position).getAvatar()).into(imvAvatar);
        }
    }
}
