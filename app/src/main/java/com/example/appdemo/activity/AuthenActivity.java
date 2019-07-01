package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.appdemo.R;
import com.example.appdemo.adapter.AuthenViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class AuthenActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    AuthenViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);

        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        adapter = new AuthenViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
