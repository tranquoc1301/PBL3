package com.example.myapplication.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> listFragment;
    public ScreenSlidePagerAdapter(FragmentActivity fa, ArrayList<Fragment> listFragment) {
        super(fa);
        this.listFragment = listFragment;
    }
    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    public void setListFragment(ArrayList<Fragment> listFragment) {
        this.listFragment = listFragment;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return listFragment.size();
    }

}