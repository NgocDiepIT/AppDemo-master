package com.example.appdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
public static void showToast(Context context, String content){
    Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
}
}
