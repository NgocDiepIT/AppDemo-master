package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.adapter.MessageByGroupAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.response.Message;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.APIStringRoot;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageByGroupActivity extends AppCompatActivity {
    TextView tvNameGroup;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    private RetrofitService retrofitService;
    EditText edtMess;
    ImageView ivSend, ivBack;
    MessageByGroupAdapter messageAdapter;
    ArrayList<Message> messageArrayList;
    String groupId, groupName;
    UserInfor userInfor;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCLEVIEW = 2;
    private Socket socket;
    {
        try {
            socket = IO.socket(APIStringRoot.API_ROOT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_by_group);
        init();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    private void init() {
        socket.connect();

        userInfor = RealmContext.getInstance().getUser();
        Intent intent = getIntent();
        groupId = intent.getStringExtra("GetGroupId");
        groupName = intent.getStringExtra("GetGroupName");

        socket.emit("join_chat", groupId, userInfor.getUserId());
        socket.on("new_message", onNewMessage);

        tvNameGroup = findViewById(R.id.tv_namegroup);
        tvNameGroup.setText(groupName);
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_mess);
        edtMess = findViewById(R.id.edt_message);
        ivSend = findViewById(R.id.iv_send);
        ivBack = findViewById(R.id.iv_back);

        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);

        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageByGroupAdapter(messageArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MessageByGroupActivity.this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MessageByGroupActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (args != null) {
                        String data = args[0].toString();
                        Message newMessage = (new Gson()).fromJson(data, Message.class);

                        messageArrayList.add(newMessage);
                        messageAdapter.notifyDataSetChanged();

                        if (!messageArrayList.isEmpty()) {
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }
                    }
                }
            });
        }
    };

    private void addListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtMess.getText().toString();
                if (!content.isEmpty()) {
                    socket.emit("create_messge", groupId, userInfor.getUserId(), content);
                    edtMess.setText("");
                } else {
                    Utils.showToast(MessageByGroupActivity.this, "You must input content before sending!");
                }
            }
        });
    }

    private void getAllMessage() {
        retrofitService.getAllMessage(groupId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                ArrayList<Message> messages = (ArrayList<Message>) response.body();
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
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Utils.showToast(MessageByGroupActivity.this, "No Internet!");
            }
        });
    }
}
