package com.example.appdemo.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appdemo.fragment.FriendFragment;
import com.example.appdemo.fragment.LoginFragment;
import com.example.appdemo.fragment.MessageFragment;
import com.example.appdemo.fragment.ProfileFragment;
import com.example.appdemo.fragment.RegisterFragment;
import com.example.appdemo.fragment.StatusFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_NUMBER = 4;
    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StatusFragment();
            case 1:
                return new FriendFragment();
            case 2:
                return new MessageFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "Home";
//            case 1:
//                return "Friend";
//        }
//        return null;
//    }
}
