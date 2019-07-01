package com.example.appdemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdemo.R;

public class RegisterFragment extends Fragment {
    EditText edtUsername, edtPassword, edtRePassword, edtFullname, edtDoB, edtAdress, edtPhone;
    Button btnRegister;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPass);
        edtRePassword = view.findViewById(R.id.re_edtPass);
        edtFullname = view.findViewById(R.id.edtFullName);
        edtDoB = view.findViewById(R.id.edtDoB);
        edtAdress = view.findViewById(R.id.edtAdress);
        edtPhone = view.findViewById(R.id.edtPhone);
        btnRegister = view.findViewById(R.id.btnRegister);

        addListener();
        return view;
    }

    private void addListener() {
    }


}
