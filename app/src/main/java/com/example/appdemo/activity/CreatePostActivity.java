package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.response.UserInfor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreatePostActivity extends AppCompatActivity {
    @BindView(R.id.iv_ava)
    CircleImageView ivAva;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.edt_content)
    EditText tvContent;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.btn_post)
    Button btnPost;
    @BindView(R.id.btn_float)
    FloatingActionButton btnAdd;

    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two_1)
    ImageView ivTwo1;
    @BindView(R.id.iv_two_2)
    ImageView ivTwo2;
    @BindView(R.id.iv_three_1)
    ImageView ivThree1;
    @BindView(R.id.iv_three_2)
    ImageView ivThree2;
    @BindView(R.id.iv_three_3)
    ImageView ivThree3;
    @BindView(R.id.iv_four_1)
    ImageView ivFour1;
    @BindView(R.id.iv_four_2)
    ImageView ivFour2;
    @BindView(R.id.iv_four_3)
    ImageView ivFour3;
    @BindView(R.id.iv_four_4)
    ImageView ivFour4;
    UserInfor userInfor;
    String[] uriList;
    int countImage;
    final int MODE_ONE = 0;
    final int MODE_TWO = 1;
    final int MODE_THREE = 2;
    final int MODE_FOUR = 3;
    final int MODE_MORE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        init();
        addListener();
    }

    private void init() {
        ButterKnife.bind(this);
        userInfor = RealmContext.getInstance().getUser();
        Glide.with(CreatePostActivity.this).load(userInfor.getAvatar()).into(ivAva);
        tvUsername.setText(userInfor.getFullName());
        Intent intent = getIntent();
        countImage = intent.getIntExtra("GetCountImage", 0);
        uriList = intent.getStringArrayExtra("GetUri");
    }

    private void addListener() {
        if (countImage == 1) {
            viewFlipper.setDisplayedChild(MODE_ONE);
            ivOne.setImageURI(Uri.parse(uriList[0]));
        } else if (countImage == 2) {
            viewFlipper.setDisplayedChild(MODE_TWO);
            ivTwo1.setImageURI(Uri.parse(uriList[0]));
            ivTwo2.setImageURI(Uri.parse(uriList[1]));
        } else if (countImage == 3) {
            viewFlipper.setDisplayedChild(MODE_THREE);
            ivThree1.setImageURI(Uri.parse(uriList[0]));
            ivThree2.setImageURI(Uri.parse(uriList[1]));
            ivThree3.setImageURI(Uri.parse(uriList[2]));
        } else if (countImage == 4) {
            viewFlipper.setDisplayedChild(MODE_FOUR);
            ivFour1.setImageURI(Uri.parse(uriList[0]));
            ivFour2.setImageURI(Uri.parse(uriList[1]));
            ivFour3.setImageURI(Uri.parse(uriList[2]));
            ivFour4.setImageURI(Uri.parse(uriList[3]));
        } else {
            viewFlipper.setDisplayedChild(MODE_MORE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePostActivity.this, AddImageOnPostActivity.class);
                startActivity(intent);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
