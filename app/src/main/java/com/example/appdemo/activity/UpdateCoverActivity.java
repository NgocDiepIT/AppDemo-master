package com.example.appdemo.activity;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.adapter.UpdateCoverAdapter;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.interf.OnDeleteImageClickListener;
import com.example.appdemo.json_models.request.UpdateCoverPhotoSendForm;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class UpdateCoverActivity extends AppCompatActivity implements OnDeleteImageClickListener {
    @BindView(R.id.btn_float)
    FloatingActionButton btn_add;
    //    @BindView(R.id.iv_ava)
//    ImageView ivAva;
//    @BindView(R.id.tv_username)
//    TextView tvUsername;
    @BindView(R.id.rv_image)
    RecyclerView recyclerView;
    @BindView(R.id.iv_ok)
    ImageView ivOk;
    UpdateCoverAdapter coverAdapter;
    List<Uri> imageList;
    UserInfor userInfor;
    String[] listPermissions = null;
    public static final int REQUEST_PERMISSION_CODE = 1;
    public static final int REQUEST_GET_IMAGE_CODE = 2;
    private StorageReference storageReference;
    List<String> uriResponseList;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cover);
        init();
        addListener();
    }

    private void init() {
        ButterKnife.bind(this);
        uriResponseList = new ArrayList<>();
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        userInfor = RealmContext.getInstance().getUser();
//        tvUsername.setText(userInfor.getFullName());
//        Glide.with(UpdateCoverActivity.this).load(userInfor.getAvatar()).into(ivAva);

        storageReference = FirebaseStorage.getInstance().getReference();
        imageList = new ArrayList<>();
        coverAdapter = new UpdateCoverAdapter(imageList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateCoverActivity.this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(coverAdapter);
    }

    private void addListener() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensurePermission();
            }
        });

//        Log.d("bkhub", valueOf(imageList.size()));
        ivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageList.clear();
                for(int i = 0; i < imageList.size(); i++){
                    uploadImage(imageList.get(i));
                }
                Utils.showToast(UpdateCoverActivity.this, "Done!");
            }
        });
    }

    private void ensurePermission() {
        listPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (checkPermission(this, listPermissions)) {
            openGallery();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(listPermissions, REQUEST_PERMISSION_CODE);
            }
        }
    }

    private boolean checkPermission(Context context, String[] listPermissions) {
        if (context != null && listPermissions != null) {
            for (String permission : listPermissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GET_IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (checkPermission(UpdateCoverActivity.this, listPermissions)) {
                openGallery();
            } else {
                Utils.showToast(UpdateCoverActivity.this, "Request is denied!");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_IMAGE_CODE) {
            Uri uri = data.getData();
//            imageList.clear();
            imageList.add(uri);
            coverAdapter.notifyDataSetChanged();
        }

        if (imageList.size() > 0) {
            ivOk.setVisibility(View.VISIBLE);
        } else ivOk.setVisibility(View.INVISIBLE);
    }

    private void uploadImage(Uri uri) {
        StorageReference ref = storageReference.child("cover/" + uri.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(uri);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
//                    Log.d("bkhub", task.getResult().toString());
                    uriResponseList.add(task.getResult().toString());
                }
                Log.d("bkhub", valueOf(uriResponseList.size()));
                updateCoverPhoto();
            }
        });
    }

    private void updateCoverPhoto(){
        UpdateCoverPhotoSendForm sendForm = new UpdateCoverPhotoSendForm(uriResponseList);
        retrofitService.updateCoverPhoto(userInfor.getUserId(), sendForm).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    finish();
                } else {
                    Utils.showToast(UpdateCoverActivity.this, "Fail!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Utils.showToast(UpdateCoverActivity.this, "No Internet!");
            }
        });
    }

    @Override
    public void onDeleteImage(Uri uri) {
        imageList.remove(uri);
        coverAdapter.notifyDataSetChanged();

        if (imageList.size() > 0) {
            ivOk.setVisibility(View.VISIBLE);
        } else ivOk.setVisibility(View.INVISIBLE);
    }
}
