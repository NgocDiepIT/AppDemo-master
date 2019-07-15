package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.adapter.AddConservationAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemAddGroupClickListener;
import com.example.appdemo.interf.OnItemFriendClickListener;
import com.example.appdemo.json_models.request.CreateGroupChatSendForm;
import com.example.appdemo.json_models.response.GroupChatCreate;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class AddConservationActivity extends AppCompatActivity implements OnItemAddGroupClickListener {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    UserInfor user;
    ArrayList<UserInfor> friendList;
    AddConservationAdapter friendAdapter;
    RetrofitService retrofitService;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCLEVIEW = 2;
    ImageView ivOk, ivBack;
    EditText edtNameGroup;
    String nameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conservation);
        init();
        getAllFriend();
        addListener();
    }

    private void init() {
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_list_friend);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        ivOk = findViewById(R.id.iv_ok);
        ivBack = findViewById(R.id.iv_back);
        user = RealmContext.getInstance().getUser();

        friendList = new ArrayList<>();
        friendAdapter = new AddConservationAdapter(friendList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddConservationActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendAdapter);
        edtNameGroup = findViewById(R.id.edt_name_group);
    }

    private void getAllFriend() {
        retrofitService.getAllFriend(user.getUserId()).enqueue(new Callback<ArrayList<UserInfor>>() {
            @Override
            public void onResponse(Call<ArrayList<UserInfor>> call, Response<ArrayList<UserInfor>> response) {
                ArrayList<UserInfor> friends = response.body();
                if (response.code() == 200 && friends != null && !friends.isEmpty()) {
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
                Utils.showToast(AddConservationActivity.this, "No Internet!");
            }
        });
    }

    private void addListener(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createGroupWithName(List<UserInfor> userInforList){
        String[] users = new String[userInforList.size()];
        String[] memberName = new String[userInforList.size()];

        for(int i=0; i < userInforList.size(); i++){
            users[i] = userInforList.get(i).getUserId();
        }

        for(int i=0; i < userInforList.size(); i++){
            memberName[i] = userInforList.get(i).getFullName();
        }

        CreateGroupChatSendForm sendForm = new CreateGroupChatSendForm(users);
        retrofitService.createGroupChat(sendForm).enqueue(new Callback<GroupChatCreate>() {
            @Override
            public void onResponse(Call<GroupChatCreate> call, Response<GroupChatCreate> response) {
                GroupChatCreate groupChat = response.body();
                if(response.code() == 200 && groupChat != null){
                    Utils.showToast(AddConservationActivity.this, "Done!");

                    Intent intent = new Intent(AddConservationActivity.this, MessageByGroupActivity.class);
                    intent.putExtra("GetGroupName", nameGroup);
                    intent.putExtra("GetGroupId", groupChat.get_id());
                    intent.putExtra("GetMember", memberName);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GroupChatCreate> call, Throwable t) {
                Utils.showToast(AddConservationActivity.this, "No Internet!");
            }
        });
    }

    private void createGroupWithoutName(List<UserInfor> userInforList){
        String[] users = new String[userInforList.size()];
        String[] userName = new String[userInforList.size()];
        String[] memberName = new String[userInforList.size()];
        String name;
        for(int i=0; i < userInforList.size(); i++){
            users[i] = userInforList.get(i).getUserId();
        }

        for(int i=0; i < userInforList.size(); i++){
            memberName[i] = userInforList.get(i).getFullName();
        }

        for(int i=0; i < 2; i++){
            userName[i] = userInforList.get(i).getFullName();
        }

        if(userName.length == 2){
            name = userName[0] + ", " + userName[1];
        } else {
            name = userName[0] + ", " + userName[1] + ", +" + valueOf(users.length - 2);
        }
//        Log.d("bkhub", name);

        CreateGroupChatSendForm sendForm = new CreateGroupChatSendForm(users);
        retrofitService.createGroupChat(sendForm).enqueue(new Callback<GroupChatCreate>() {
            @Override
            public void onResponse(Call<GroupChatCreate> call, Response<GroupChatCreate> response) {
                GroupChatCreate groupChat = response.body();
                if(response.code() == 200 && groupChat != null){
                    Utils.showToast(AddConservationActivity.this, "Done!");

                    Intent intent = new Intent(AddConservationActivity.this, MessageByGroupActivity.class);
                    intent.putExtra("GetGroupId", groupChat.get_id());
                    intent.putExtra("GetGroupName", name);
//                    intent.putExtra("GetMember", memberName);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GroupChatCreate> call, Throwable t) {
                Utils.showToast(AddConservationActivity.this, "No Internet!");
            }
        });
    }

    @Override
    public void createGroupChat(List<UserInfor> userInforList) {
        if(userInforList.size() > 1) {
            ivOk.setVisibility(View.VISIBLE);

            ivOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddConservationActivity.this);
                    LayoutInflater inflater = AddConservationActivity.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.layout_dialog_name_group, null);
                    builder.setView(dialogView);
                    builder.setCancelable(false);

                    edtNameGroup = dialogView.findViewById(R.id.edt_name_group);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nameGroup = edtNameGroup.getText().toString();
                            if (nameGroup.isEmpty()) {
                                Utils.showToast(AddConservationActivity.this, "You didn't input namegroup!");
                            } else {
                                createGroupWithName(userInforList);
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            createGroupWithoutName(userInforList);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        } else {
            ivOk.setVisibility(View.INVISIBLE);
        }
    }
}
