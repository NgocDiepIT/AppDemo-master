package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.appdemo.R;
import com.example.appdemo.adapter.StatusAdapter;
import com.example.appdemo.interf.OnItemStatusClickListener;
import com.example.appdemo.json_models.request.LikeStatusSendForm;
import com.example.appdemo.json_models.response.ProfileUser;
import com.example.appdemo.json_models.response.Status;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFriendActivity extends AppCompatActivity implements OnItemStatusClickListener{
    private RetrofitService retrofitService;
    UserInfor userInfor;
    SliderLayout sliderLayout;
    TextView tvUsername, tvAddress, tvDoB, tvPhone;
    ViewFlipper viewFlipper;
    final int MODE_NODATA = 1;
    final int MODE_RECYCLEVIEW = 2;
    ArrayList<Status> statusArrayList;
    RecyclerView recyclerView;
    LinearLayout itemAddress, itemPhone;
    ImageView ivAva;

    StatusAdapter statusAdapter;
    OnItemStatusClickListener listener;
    String userId, username, avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_friend);
        init();
        addListener();
        Glide.with(ProfileFriendActivity.this).load(avatarUrl).into(ivAva);

        getProfile(username, userId);
    }

    private void addListener() {
        itemAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc = tvAddress.getText().toString();
                Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
                startActivity(intent);
            }
        });

        itemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = tvPhone.getText().toString();
                if(!TextUtils.isEmpty(tel)) {
                    String dial = "tel:" + tel;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("GetUserId");
        username = intent.getStringExtra("GetUsername");
        avatarUrl = intent.getStringExtra("GetAvatarUrl");

        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        sliderLayout = findViewById(R.id.slider);
        tvUsername = findViewById(R.id.tv_username);
        tvAddress = findViewById(R.id.tv_address);
        tvDoB = findViewById(R.id.tv_birthday);
        tvPhone = findViewById(R.id.tv_phone);
        viewFlipper = findViewById(R.id.flipper_status);
        recyclerView = findViewById(R.id.rv_status);
        ivAva = findViewById(R.id.iv_ava);
        itemAddress = findViewById(R.id.item_address);
        itemPhone = findViewById(R.id.item_phone);

        statusArrayList = new ArrayList<>();

        statusAdapter = new StatusAdapter(this, statusArrayList);
        recyclerView.setAdapter(statusAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProfileFriendActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getProfile(String username, String userId) {
        retrofitService.getProfileUser(username, userId).enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                ProfileUser profileUser = response.body();
                if (response.code() == 200 && profileUser != null) {
                    tvUsername.setText(profileUser.getFullName());
                    tvAddress.setText(profileUser.getAddress());
                    tvDoB.setText(profileUser.getBirthday());
                    tvPhone.setText(profileUser.getPhone());

                    ArrayList<Status> statuses = profileUser.getPostList();
                    if (statuses != null) {
                        statusArrayList.clear();
                        statusArrayList.addAll(statuses);
//                        statusProfileAdapter.notifyDataSetChanged();
                        statusAdapter.notifyDataSetChanged();
                        viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                    } else {
                        viewFlipper.setDisplayedChild(MODE_NODATA);
                    }

//                    String[] coverPhoto = profileUser.getCoverPhoto();
                    showSlider(profileUser.getCoverPhoto());
                } else {
                    Utils.showToast(ProfileFriendActivity.this, "Fail!");
                }
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Utils.showToast(ProfileFriendActivity.this, "No Internet!");
            }
        });
    }

    private void showSlider(String[] coverPhoto) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (String url : coverPhoto) {
//            TextSliderView textSliderView = new TextSliderView(getContext());
            DefaultSliderView textSliderView = new DefaultSliderView(ProfileFriendActivity.this);
            textSliderView
                    .image(url)
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .bundle(new Bundle());

            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);//đặt hiệu ứng
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//dấu 3 chấm nằm ở trung tâm ảnh
        sliderLayout.setDuration(4000);//đặt thời gian tự động chuyển ảnh
    }

    private void likePost(Status status) {
        LikeStatusSendForm sendForm = new LikeStatusSendForm(userInfor.getUserId(), status.getPostId());
        retrofitService.likePost(sendForm).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    status.setLike(!status.isLike());
                    if (status.isLike()) {
                        status.setNumberLike(status.getNumberLike() + 1);
                    } else {
                        status.setNumberLike(status.getNumberLike() - 1);
                    }
                    statusAdapter.notifyDataSetChanged();
                } else {
                    Utils.showToast(ProfileFriendActivity.this, "Fail!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.showToast(ProfileFriendActivity.this, "No Internet!");
            }
        });
    }


    @Override
    public void onLikeClick(Status status) {
        likePost(status);
    }

    @Override
    public void onCommentClick(Status status) {
        Intent intent = new Intent(ProfileFriendActivity.this, CommentActivity.class);
        intent.putExtra("GetPostId", status.getPostId());
        intent.putExtra("GetUserId", userInfor.getUserId());
        startActivity(intent);
    }

    @Override
    public void onEditStatus(Status status) {

    }

    @Override
    public void onDeleteStatus(Status status) {

    }
}
