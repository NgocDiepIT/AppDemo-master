package com.example.appdemo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.appdemo.R;
import com.example.appdemo.adapter.UpdateCoverAdapter;
import com.example.appdemo.interf.OnDeleteImageClickListener;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.utils.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

public class AddImageOnPostActivity extends AppCompatActivity implements OnDeleteImageClickListener {
    @BindView(R.id.btn_float)
    FloatingActionButton btnAdd;
    @BindView(R.id.rv_image)
    RecyclerView recyclerView;
    @BindView(R.id.iv_ok)
    ImageView ivOk;
    UpdateCoverAdapter imageAdapter;
    List<Uri> imageList;
    String[] listPermissions = null;
    public static final int REQUEST_PERMISSION_CODE = 1;
    public static final int REQUEST_GET_IMAGE_CODE = 2;
    private StorageReference storageReference;
    List<String> uriResponseList;
    String[] uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cover);
        init();
        addListener();
    }

    private void init() {
        ButterKnife.bind(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        imageList = new ArrayList<>();
        imageAdapter = new UpdateCoverAdapter(imageList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddImageOnPostActivity.this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imageAdapter);
        uriResponseList = new ArrayList<>();
    }

    private void addListener(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensurePermission();
            }
        });

        ivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < imageList.size(); i++){
                    uploadImage(imageList.get(i));
                }

            }
        });
    }

    private void uploadImage(Uri uri) {
        StorageReference ref = storageReference.child("postImage/" + uri.getLastPathSegment());
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
                    uriResponseList.add(task.getResult().toString());
                }

                for(int i = 0; i < uriResponseList.size(); i++){
                    uris[i] = uriResponseList.get(i);
                }
                Intent intent = new Intent(AddImageOnPostActivity.this, CreatePostActivity.class);
                intent.putExtra("GetCountImage", uriResponseList.size());
                intent.putExtra("GetUri", uris);
                startActivity(intent);
            }
        });
    }

    private void ensurePermission() {
        listPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (checkPermission(AddImageOnPostActivity.this, listPermissions)) {
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
            if (checkPermission(AddImageOnPostActivity.this, listPermissions)) {
                openGallery();
            } else {
                Utils.showToast(AddImageOnPostActivity.this, "Request is denied!");
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
            imageAdapter.notifyDataSetChanged();
        }

        if (imageList.size() > 0) {
            ivOk.setVisibility(View.VISIBLE);
        } else ivOk.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDeleteImage(Uri uri) {
        imageList.remove(uri);
        imageAdapter.notifyDataSetChanged();

        if (imageList.size() > 0) {
            ivOk.setVisibility(View.VISIBLE);
        } else ivOk.setVisibility(View.INVISIBLE);
    }
}
