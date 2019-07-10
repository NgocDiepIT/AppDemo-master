package com.example.appdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemAddGroupClickListener;
import com.example.appdemo.interf.OnItemFriendClickListener;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddConservationAdapter extends RecyclerView.Adapter<AddConservationAdapter.MyViewHolder> {
    ArrayList<UserInfor> friendList;
    private OnItemAddGroupClickListener listener;
    UserInfor user;
    final int MODE_ADD = 0;
    final int MODE_TICK = 1;
    List<UserInfor> userInforList;

    public AddConservationAdapter(ArrayList<UserInfor> friendList, OnItemAddGroupClickListener listener) {
        this.friendList = friendList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddConservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_add_friend_chat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddConservationAdapter.MyViewHolder holder, int position) {
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
        ImageView ivAdd;
        ViewFlipper viewFlipper;

        RelativeLayout itemAdd;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            user = RealmContext.getInstance().getUser();
            context = itemView.getContext();
            imvAvatar = itemView.findViewById(R.id.iv_ava);
            tvFullName = itemView.findViewById(R.id.tv_full_name);
            ivAdd = itemView.findViewById(R.id.iv_add);
            itemAdd = itemView.findViewById(R.id.item_add_conservation);
            viewFlipper = itemView.findViewById(R.id.view_flipper);
            userInforList = new ArrayList<>();
            userInforList.add(user);
        }

        private void bindView(UserInfor userInfor) {
            Glide.with(context).load(userInfor.getAvatar()).into(imvAvatar);
            tvFullName.setText(userInfor.getFullName());
            userInfor.setAdded(false);

            itemAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    userInfor.setAdded(!userInfor.isAdded());

//                    viewFlipper.setDisplayedChild(userInfor.isAdded() ? MODE_TICK : MODE_ADD);

                    if (userInfor.isAdded()) {
                        viewFlipper.setDisplayedChild(MODE_TICK);
                        userInforList.add(userInfor);
                    } else {
                        viewFlipper.setDisplayedChild(MODE_ADD);
                        if (userInforList.contains(userInfor)) {
                            userInforList.remove(userInfor);
                        }
                    }
                    listener.createGroupChat(userInforList);
                }
            });

        }

    }
}
