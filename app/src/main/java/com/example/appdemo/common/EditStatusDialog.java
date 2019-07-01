package com.example.appdemo.common;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.appdemo.R;
import com.example.appdemo.interf.OnUpdateDialogListener;
import com.example.appdemo.json_models.request.UpdateStatusSendForm;

public class EditStatusDialog extends Dialog {
    EditText edtEditStatus;
    Button btnCancel, btnSave;
    OnUpdateDialogListener listener;

    public EditStatusDialog(@NonNull Context context, OnUpdateDialogListener listener) {
        super(context);
        this.listener = listener;
        init();
        addListener();
    }

    private void addListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditStatusDialog.this.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtEditStatus.getText().toString().isEmpty()){
                    listener.onSaveClick(edtEditStatus.getText().toString());
                    EditStatusDialog.this.dismiss();
                }
            }
        });
    }

    private void init() {
        this.setContentView(R.layout.layout_dialog_edit_status);
        Window window = getWindow();
        if(window != null){
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        edtEditStatus = findViewById(R.id.edt_post);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    public void setContent(String content){
        edtEditStatus.setText(content);
    }
}
