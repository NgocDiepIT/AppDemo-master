package com.example.appdemo.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.activity.AuthenActivity;
import com.example.appdemo.activity.ProfileFriendActivity;
import com.example.appdemo.adapter.FriendAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemFriendClickListener;
import com.example.appdemo.json_models.response.Friend;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.APIStringRoot;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment implements OnItemFriendClickListener {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    UserInfor user;

    ArrayList<UserInfor> friendList;
    FriendAdapter friendAdapter;
    RetrofitService retrofitService;
//    Button btnLogOut;
    final int CODE_OK = 200;
    final int MODE_PROGRESS_BAR = 0;
    final int MODE_NO_DATA = 1;
    final int MODE_NO_INTERNET = 2;
    final int MODE_RECYCLEVIEW = 3;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addListener();
        getAllFriend();
    }

    private void getAllFriend() {
        retrofitService.getAllFriend(user.getUserId()).enqueue(new Callback<ArrayList<UserInfor>>() {
            @Override
            public void onResponse(Call<ArrayList<UserInfor>> call, Response<ArrayList<UserInfor>> response) {
                ArrayList<UserInfor> friends = response.body();
                if (response.code() == CODE_OK && friends != null && !friends.isEmpty()) {
                    friendList.clear();
                    friendList.addAll(friends);
                    friendAdapter.notifyDataSetChanged();

                    viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserInfor>> call, Throwable t) {

                viewFlipper.setDisplayedChild(MODE_NO_INTERNET);
            }
        });

    }

    private void addListener() {
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RealmContext.getInstance().deleteAllUser();
//                gotoLogin();
//            }
//        });
    }

//    private void gotoLogin() {
//        Intent intent = new Intent(getActivity(), AuthenActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//    }

    private void init(View view) {
        viewFlipper = view.findViewById(R.id.view_flipper);
        recyclerView = view.findViewById(R.id.rv_list_friend);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        user = RealmContext.getInstance().getUser();
        friendList = new ArrayList<>();
        friendAdapter = new FriendAdapter(friendList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendAdapter);
//        btnLogOut = view.findViewById(R.id.btn_logout);
    }

    @Override
    public void viewProfileFriend(UserInfor userInfor) {
        Intent intent = new Intent(getActivity(), ProfileFriendActivity.class);
        intent.putExtra("GetUserId", userInfor.getUserId());
        intent.putExtra("GetUsername", userInfor.getUsername());
        intent.putExtra("GetAvatarUrl", userInfor.getAvatar());
        startActivity(intent);
    }
}
