package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.adapter.MemberOfGroupAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.response.GroupChat;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberOfGroupActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    RetrofitService retrofitService;
    TextView tvNameGroup;
    ImageView ivBack;
    MemberOfGroupAdapter memberAdapter;
    ArrayList<UserInfor> memberArrayList;
    UserInfor userInfor;
    String groupId;
    GroupChat groupChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_of_group);
        init();
        addListener();
        getMemberFromGroupChat();
    }

    private void init() {
        Intent intent = getIntent();
        userInfor = RealmContext.getInstance().getUser();
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_member);
        memberArrayList = new ArrayList<>();
        ivBack = findViewById(R.id.iv_back);
        tvNameGroup = findViewById(R.id.tv_namegroup);
        tvNameGroup.setText(intent.getStringExtra("GetGroupName"));
        groupId = intent.getStringExtra("GetGroupId");

        memberAdapter = new MemberOfGroupAdapter(memberArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MemberOfGroupActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(memberAdapter);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);

//        memberName = intent.getStringArrayExtra("GetMember");
//        for(int i=0; i<memberName.length; i++){
//            memberArrayList.add(memberName[i]);
//        }
    }

    private void getMemberFromGroupChat() {

        retrofitService.getGroup(userInfor.getUserId()).enqueue(new Callback<ArrayList<GroupChat>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupChat>> call, Response<ArrayList<GroupChat>> response) {
                ArrayList<GroupChat> groupChats = response.body();
                if (response.code() == 200 && groupChats != null) {
                    for (int i = 0; i < groupChats.size(); i++) {
                        if(groupChats.get(i).getGroupId().equals(groupId)){
                            groupChat = groupChats.get(i);
                            Log.d("bkhub", "Equals");
                        }
                    }

                    memberArrayList.clear();
                    memberArrayList.addAll(groupChat.getUsers());
                    memberAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(1);
                } else {
                    Utils.showToast(MemberOfGroupActivity.this, "Fail while getting members of group!");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GroupChat>> call, Throwable t) {
                Utils.showToast(MemberOfGroupActivity.this, "No Internet!");
            }
        });
    }

    private void addListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
