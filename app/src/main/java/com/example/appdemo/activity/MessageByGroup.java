package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.adapter.MessageAdapter;
import com.example.appdemo.json_models.response.Message;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageByGroup extends AppCompatActivity {
    TextView tvNameGroup;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    private RetrofitService retrofitService;
    EditText edtMess;
    ImageView ivSend;
    MessageAdapter messageAdapter;
    ArrayList<Message> messageArrayList;
    String groupId, groupName;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCLEVIEW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_by_group);
        init();
        getAllMessage();
    }

    private void init() {
        Intent intent = getIntent();
        groupId = intent.getStringExtra("GetGroupId");
        groupName = intent.getStringExtra("GetGroupName");
        tvNameGroup = findViewById(R.id.tv_namegroup);
        tvNameGroup.setText(groupName);
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_mess);
        edtMess = findViewById(R.id.edt_message);
        ivSend = findViewById(R.id.iv_send);

        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MessageByGroup.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
//        recyclerView.setFrom
    }

    private void getAllMessage() {
        retrofitService.getAllMessage(groupId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                if (response.code() == 200 && messages != null) {
                    messageArrayList.clear();
                    messageArrayList.addAll(messages);
                    messageAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
            }

                @Override
                public void onFailure (Call < List < Message >> call, Throwable t){
                    Utils.showToast(MessageByGroup.this, "No Internet!");
                }
            });
        }
    }
