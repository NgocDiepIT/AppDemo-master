package com.example.appdemo.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.appdemo.R;
import com.example.appdemo.activity.AuthenActivity;
import com.example.appdemo.activity.CommentActivity;
import com.example.appdemo.adapter.StatusProfileAdapter;
import com.example.appdemo.common.EditStatusDialog;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnItemStatusClickListener;
import com.example.appdemo.interf.OnUpdateDialogListener;
import com.example.appdemo.json_models.request.LikeStatusSendForm;
import com.example.appdemo.json_models.request.UpdateStatusSendForm;
import com.example.appdemo.json_models.response.ProfileUser;
import com.example.appdemo.json_models.response.Status;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements OnItemStatusClickListener, OnUpdateDialogListener {
    private RetrofitService retrofitService;
    UserInfor userInfor;
    SliderLayout sliderLayout;
    TextView tvUsername, tvAddress, tvDoB, tvPhone;
    ViewFlipper viewFlipper;
    final int MODE_NODATA = 1;
    final int MODE_RECYCLEVIEW = 2;
    ArrayList<Status> statusArrayList;
    RecyclerView recyclerView;
    StatusProfileAdapter statusProfileAdapter;
    ImageView ivAva, newfeedAva;
    Status currentStatus;
    TextView tvMenu;
    LinearLayout itemAddress, itemPhone;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        addListener();

        Glide.with(getActivity()).load(userInfor.getAvatar()).into(ivAva);
        Glide.with(getActivity()).load(userInfor.getAvatar()).into(newfeedAva);

        getProfile(userInfor.getUsername(), userInfor.getUserId());
        return view;
    }

    private void addListener() {
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), tvMenu);
                popupMenu.inflate(R.menu.logout_option_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.option_logout:
                                RealmContext.getInstance().deleteAllUser();
                                gotoLogin();
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        itemAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc = tvAddress.getText().toString();
// Parse the location and create the intent.
                Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
// Find an activity to handle the intent, and start that activity.
//                if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
//                } else {
//                    Log.d("ImplicitIntents", "Can't handle this intent!");
//                }
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

    private void gotoLogin() {
        Intent intent = new Intent(getActivity(), AuthenActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void init(View view) {
        userInfor = RealmContext.getInstance().getUser();
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        sliderLayout = view.findViewById(R.id.slider);
        tvUsername = view.findViewById(R.id.tv_username);
        tvAddress = view.findViewById(R.id.tv_address);
        tvDoB = view.findViewById(R.id.tv_birthday);
        tvPhone = view.findViewById(R.id.tv_phone);
        viewFlipper = view.findViewById(R.id.flipper_status);
        recyclerView = view.findViewById(R.id.rv_status);
        ivAva = view.findViewById(R.id.iv_ava);
        newfeedAva = view.findViewById(R.id.newfeeds_ava);
        tvMenu = view.findViewById(R.id.tv_menu);
        itemAddress = view.findViewById(R.id.item_address);
        itemPhone = view.findViewById(R.id.item_phone);

        statusArrayList = new ArrayList<>();
        statusProfileAdapter = new StatusProfileAdapter(statusArrayList, this);
        recyclerView.setAdapter(statusProfileAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getProfile(String username, String userId) {
        retrofitService.getProfileUser(username, userId).enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                ProfileUser profileUser = response.body();
                if (response.code() == 200 && profileUser != null) {
//                    Log.d("bkhub", profileUser.toString());
                    tvUsername.setText(profileUser.getFullName());
                    tvAddress.setText(profileUser.getAddress());
                    tvDoB.setText(profileUser.getBirthday());
                    tvPhone.setText(profileUser.getPhone());

                    ArrayList<Status> statuses = profileUser.getPostList();
                    if (statuses != null) {
                        statusArrayList.clear();
                        statusArrayList.addAll(statuses);
                        statusProfileAdapter.notifyDataSetChanged();
                        viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                    } else {
                        viewFlipper.setDisplayedChild(MODE_NODATA);
                    }

//                    String[] coverPhoto = profileUser.getCoverPhoto();
                    showSlider(profileUser.getCoverPhoto());
                } else {
                    Utils.showToast(getActivity(), "Fail!");
                }
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }

    private void showSlider(String[] coverPhoto) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (String url : coverPhoto) {
//            TextSliderView textSliderView = new TextSliderView(getContext());
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
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

    @Override
    public void onLikeClick(Status status) {
        likePost(status);
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
                    statusProfileAdapter.notifyDataSetChanged();
                } else {
                    Utils.showToast(getActivity(), "Fail!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }

    @Override
    public void onCommentClick(Status status) {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
//        Log.d("bkhub", status.getPostId());
        intent.putExtra("GetPostId", status.getPostId());
        intent.putExtra("GetUserId", userInfor.getUserId());
        startActivity(intent);
    }

    @Override
    public void onEditStatus(Status status) {
        currentStatus = status;
        EditStatusDialog dialog = new EditStatusDialog(getContext(), this);
        dialog.setContent(status.getContent());
        dialog.show();
    }

    @Override
    public void onDeleteStatus(Status status) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete status")
                .setMessage("Do you sure to delete this status?")
                .setIcon(R.drawable.icon_trash)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteStatus(userInfor.getUserId(), status);
                        Utils.showToast(getActivity(), "Done!");
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public void deleteStatus(String userId, Status status) {
        retrofitService.deleteStatus(status.getPostId(), userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    statusArrayList.remove(status);
                    statusProfileAdapter.notifyDataSetChanged();
                    Utils.showToast(getContext(), "Done!");
                } else Utils.showToast(getContext(), "Fail!");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.showToast(getContext(), "No Internet!");
            }
        });
    }

    @Override
    public void onSaveClick(String newContent) {
        updateStatus(userInfor.getUserId(), newContent, currentStatus.getPostId());
    }

    public void updateStatus(String userId, String newContent, String postId) {
        UpdateStatusSendForm sendForm = new UpdateStatusSendForm(userId, newContent);

        retrofitService.updateStatus(postId, sendForm).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status res = response.body();
                if (response.code() == 200 && res != null) {
                    currentStatus.setContent(res.getContent());
                    statusProfileAdapter.notifyDataSetChanged();
                    Utils.showToast(getActivity(), "Done!");
                } else Utils.showToast(getActivity(), "Fail!");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }
}
