package com.example.appdemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.example.appdemo.R;
import com.example.appdemo.activity.HomeActivity;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.request.LoginSendForm;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    ViewFlipper viewFlipper;
    RetrofitService retrofitService;
    static final int MODE_PROGRESS_BAR = 1;
    static final int MODE_BUTTON = 0;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        edtUsername = view.findViewById(R.id.edtName);
        edtPassword = view.findViewById(R.id.edtPass);
        btnLogin = view.findViewById(R.id.btnLogin);
        viewFlipper = view.findViewById(R.id.view_flipper);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);

        addListener();

        return view;
    }

    private void addListener() {
        UserInfor userInfor = RealmContext.getInstance().getUser();
        if(userInfor != null){
            goToHome();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()){
//                    Log.d("123", "CHECK");
                    Utils.showToast(getContext(), "Username or Password mustn't be empty!");
                } else {
                    login(username, password);
                }
            }
        });
    }

    private void login(String username, String password){
        viewFlipper.setDisplayedChild(MODE_PROGRESS_BAR);

        LoginSendForm loginSendForm = new LoginSendForm(username, password);
        retrofitService.login(loginSendForm).enqueue(new Callback<UserInfor>() {
            @Override
            public void onResponse(Call<UserInfor> call, Response<UserInfor> response) {
                UserInfor userInfor = response.body();

                if(response.code()==200 && userInfor != null){
//                    Log.d("it23", "Login successfully!" + userInfor.toString());
                    RealmContext.getInstance().addUser(userInfor);

                    goToHome();
                } else {
                    Log.d("it23", "Login code fail!");
                    Utils.showToast(getActivity(), "Username or Password is incorrect!");
                }
                viewFlipper.setDisplayedChild(MODE_BUTTON);
            }

            @Override
            public void onFailure(Call<UserInfor> call, Throwable t) {
                Log.d("123", "FAIL!");
                Utils.showToast(getActivity(), "Login fail!");
                viewFlipper.setDisplayedChild(MODE_BUTTON);
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
