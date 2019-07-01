package com.example.appdemo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.activity.CommentActivity;
import com.example.appdemo.adapter.StatusAdapter;
import com.example.appdemo.common.EditStatusDialog;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnUpdateDialogListener;
import com.example.appdemo.interf.OnItemStatusClickListener;
import com.example.appdemo.json_models.request.CreateStatusSendForm;
import com.example.appdemo.json_models.request.UpdateStatusSendForm;
import com.example.appdemo.json_models.request.LikeStatusSendForm;
import com.example.appdemo.json_models.response.Status;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusFragment extends Fragment implements OnItemStatusClickListener, OnUpdateDialogListener {
    private RetrofitService retrofitService;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    TextView tvPost;
    UserInfor user;
    CircleImageView newfeesAvatar, dialogAvatar;
    EditText edtPost;

    StatusAdapter statusAdapter;
    ArrayList<Status> statusList;
    OnItemStatusClickListener listener;
    final int MODE_PROGRESSBAR = 0;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCYCLEVIEW = 2;
    Status currentStatus;

    public StatusFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = RealmContext.getInstance().getUser();
        Log.d("123", user.toString());

        init(view);
        addListener();

        if (user != null) {
            Glide.with(getActivity()).load(user.getAvatar()).into(newfeesAvatar);
            getAllPost(user.getUserId());
        }
    }

    private void init(View view) {
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        viewFlipper = view.findViewById(R.id.flipper_status);
        tvPost = view.findViewById(R.id.tv_post);
        recyclerView = view.findViewById(R.id.rv_status);
        newfeesAvatar = view.findViewById(R.id.newfeeds_ava);

        statusList = new ArrayList<>();
        statusAdapter = new StatusAdapter(this, statusList);
        recyclerView.setAdapter(statusAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }

    private void addListener() {
        tvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layout_dialog_post, null);
                builder.setView(dialogView);
                builder.setCancelable(false);

                dialogAvatar = dialogView.findViewById(R.id.dialog_ava);
                edtPost = dialogView.findViewById(R.id.edt_post);

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = edtPost.getText().toString();
                        if (content.isEmpty()) {
                            Utils.showToast(getActivity(), "You didn't input content to post!");
                        } else {
                            createPost(content);
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            private void createPost(String content) {
                CreateStatusSendForm sendForm = new CreateStatusSendForm(user.getUserId(), content);
                retrofitService.createPost(sendForm).enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        Status status = response.body();
                        if (response.code() == 200 && status != null) {
                            statusList.add(0, status);
                            statusAdapter.notifyDataSetChanged();
                            edtPost.setText("");
                        } else {
                            Utils.showToast(getActivity(), "Post fail!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                        Utils.showToast(getActivity(), "No internet!");
                    }
                });
            }
        });
    }

    private void getAllPost(String userId) {
        retrofitService.getAllPost(userId).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                ArrayList<Status> statuses = (ArrayList<Status>) response.body();
                if (response.code() == 200 && statusList != null) {
                    statusList.clear();
                    statusList.addAll(statuses);
                    statusAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(MODE_RECYCYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }


    @Override
    public void onLikeClick(Status status) {
        likePost(status);
    }

    @Override
    public void onCommentClick(Status status) {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        Log.d("bkhub", status.getPostId());
        intent.putExtra("GetPostId", status.getPostId());
        intent.putExtra("GetUserId", user.getUserId());
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
                        deleteStatus(user.getUserId(), status);
                        Utils.showToast(getActivity(), "Done!");
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void deleteStatus(String userId, Status status){
        retrofitService.deleteStatus(status.getPostId(), userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    statusList.remove(status);
                    statusAdapter.notifyDataSetChanged();
                    Utils.showToast(getContext(), "Done!");
                } else Utils.showToast(getContext(), "Fail!");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.showToast(getContext(), "No Internet!");
            }
        });
    }

    private void likePost(Status status) {
        LikeStatusSendForm sendForm = new LikeStatusSendForm(user.getUserId(), status.getPostId());
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
                    Utils.showToast(getActivity(), "Fail!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }

    public void updateStatus(String userId, String newContent, String postId){
        UpdateStatusSendForm sendForm = new UpdateStatusSendForm(userId, newContent);

        retrofitService.updateStatus(postId, sendForm).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status res = response.body();
                if(response.code() == 200 && res != null){
                    currentStatus.setContent(res.getContent());
                    statusAdapter.notifyDataSetChanged();
                    Utils.showToast(getActivity(), "Done!");
                } else Utils.showToast(getActivity(), "Fail!");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }

    @Override
    public void onSaveClick(String newContent) {
        updateStatus(user.getUserId(), newContent, currentStatus.getPostId());
    }
}
