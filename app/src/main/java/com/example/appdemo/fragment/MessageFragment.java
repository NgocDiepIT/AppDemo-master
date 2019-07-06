package com.example.appdemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.activity.AddConservationActivity;
import com.example.appdemo.activity.MessageByGroup;
import com.example.appdemo.adapter.GroupChatAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemGroupChatClickListener;
import com.example.appdemo.json_models.response.GroupChat;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageFragment extends Fragment implements OnItemGroupChatClickListener {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    private RetrofitService retrofitService;
    FloatingActionButton btnFloating;
    UserInfor userInfor;
    GroupChatAdapter groupChatAdapter;
    ArrayList<GroupChat> groupChatArrayList;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCLEVIEW = 2;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        userInfor = RealmContext.getInstance().getUser();
        init(view);
        getGroup();
        addListener();
        return view;
    }

    private void init(View view) {
        btnFloating = view.findViewById(R.id.btn_float);
        viewFlipper = view.findViewById(R.id.view_flipper);
        recyclerView = view.findViewById(R.id.rv_groupchat);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);

        groupChatArrayList = new ArrayList<>();
        groupChatAdapter = new GroupChatAdapter(groupChatArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(groupChatAdapter);
    }

    private void addListener() {
        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddConservationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getGroup() {
        retrofitService.getGroup(userInfor.getUserId()).enqueue(new Callback<ArrayList<GroupChat>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupChat>> call, Response<ArrayList<GroupChat>> response) {
                ArrayList<GroupChat> groupChats = response.body();
                if (response.code() == 200 && groupChats != null) {
                    groupChatArrayList.clear();
                    groupChatArrayList.addAll(groupChats);
                    groupChatAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GroupChat>> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }


    @Override
    public void viewConservationByGroup(GroupChat groupChat) {
        Intent intent = new Intent(getActivity(), MessageByGroup.class);
        intent.putExtra("GetGroupId", groupChat.getGroupId());
        intent.putExtra("GetGroupName", groupChat.getGroupName());
        startActivity(intent);
    }
}
