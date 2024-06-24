package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;


import com.example.myapplication.R;
import com.example.myapplication.controller.ScreenSlidePagerAdapter;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ArrayList<Fragment> listFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ViewPager2 viewPager2 = findViewById(R.id.pager);
        listFragment = new ArrayList<>();
        listFragment.add(new LoginFragment());
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setViewPager(viewPager2);
        listFragment.add(registerFragment);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this, listFragment);
        viewPager2.setAdapter(pagerAdapter);
    }


}