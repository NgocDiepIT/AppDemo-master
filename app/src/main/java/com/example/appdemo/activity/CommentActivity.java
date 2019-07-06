package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.adapter.CommentAdapter;
import com.example.appdemo.adapter.StatusAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemStatusClickListener;
import com.example.appdemo.json_models.request.CommentStatusSendForm;
import com.example.appdemo.json_models.response.Comment;
import com.example.appdemo.json_models.response.CommentCreate;
import com.example.appdemo.json_models.response.Status;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity{
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    private RetrofitService retrofitService;
    EditText edtComment;
    ImageView ivSend, ivAva;
    CommentAdapter commentAdapter;
    UserInfor user;
    ArrayList<Comment> commentList;
    ArrayList<CommentCreate> commentCreates;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCYCLEVIEW = 2;
    String postId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        user = RealmContext.getInstance().getUser();
        
        init();
        if (user != null) {
            Glide.with(this).load(user.getAvatar()).into(ivAva);
            Intent intent = getIntent();
            postId = intent.getStringExtra("GetPostId");
            userId = intent.getStringExtra("GetUserId");
//            Log.d("bkhub", postId);
            getAllComment(postId);
        }

        addListener();
    }

    private void addListener() {
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtComment.getText().toString();
                if(!content.isEmpty()){
                    commentStatus(content);
                } else {
                    Utils.showToast(CommentActivity.this, "Comment mustn't be empty!");
                }
            }
        });

    }

    private void commentStatus(String content) {
        CommentStatusSendForm sendForm = new CommentStatusSendForm(userId, postId, content);
        retrofitService.commentStatus(sendForm).enqueue(new Callback<CommentCreate>() {
            @Override
            public void onResponse(Call<CommentCreate> call, Response<CommentCreate> response) {
                CommentCreate res = response.body();
                if(response.code() == 200 && res != null){
                //    commentCreates.add(commentCreates.size(), res);
                    Utils.showToast(CommentActivity.this, "Done!");
                    getAllComment(postId);
                    //commentAdapter.notifyDataSetChanged();
                    edtComment.setText("");

                } else {
                    Utils.showToast(CommentActivity.this, "Fail!");
                }
            }

            @Override
            public void onFailure(Call<CommentCreate> call, Throwable t) {
                Utils.showToast(CommentActivity.this, "No Internet!");
            }
        });
    }

    private void getAllComment(String postId) {
        retrofitService.getAllComment(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                ArrayList<Comment> comments = (ArrayList<Comment>) response.body();
                if(response.code() == 200 && comments != null){
                    commentList.clear();
                    commentList.addAll(comments);
                    commentAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(MODE_RECYCYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Utils.showToast(CommentActivity.this, "No Internet!");
            }
        });
    }

    private void init() {
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_cmt);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        ivAva = findViewById(R.id.iv_ava);
        ivSend = findViewById(R.id.iv_send);
        edtComment = findViewById(R.id.edt_comment);

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(commentAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
