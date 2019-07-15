package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdemo.R;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.request.UpdateProfileSendForm;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    @BindView(R.id.edt_fullName) EditText edtFullName;
    @BindView(R.id.edt_address) EditText edtAddress;
    @BindView(R.id.edt_DoB) EditText edtDoB;
    @BindView(R.id.edt_phone) EditText edtPhone;
    @BindView(R.id.btn_cancel) Button btnCancel;
    @BindView(R.id.btn_ok) Button btnOk;
    String fullName, address, birthday, phone;
    String updateFullName, updateAddress, updateBirthday, updatePhone;
    private RetrofitService retrofitService;
    UserInfor userInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        init();
        addListener();
    }

    private void init() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        fullName = intent.getStringExtra("GetFullName");
        address = intent.getStringExtra("GetAddress");
        birthday = intent.getStringExtra("GetDoB");
        phone = intent.getStringExtra("GetPhone");
        userInfor = RealmContext.getInstance().getUser();
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }

    private void addListener(){
        edtFullName.setText(fullName);
        edtAddress.setText(address);
        edtDoB.setText(birthday);
        edtPhone.setText(phone);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFullName = edtFullName.getText().toString();
                updateAddress = edtAddress.getText().toString();
                updateBirthday = edtDoB.getText().toString();
                updatePhone = edtPhone.getText().toString();

                UpdateProfileSendForm sendForm = new UpdateProfileSendForm(updateFullName, updateAddress, updateBirthday, updatePhone);
                retrofitService.updateProfile(userInfor.getUserId(), sendForm).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            Utils.showToast(UpdateProfileActivity.this, "Done!");
                            onBackPressed();
                        } else {
                            Utils.showToast(UpdateProfileActivity.this, "Fail!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Utils.showToast(UpdateProfileActivity.this, "No Internet!");
                    }
                });
            }
        });
    }
}
