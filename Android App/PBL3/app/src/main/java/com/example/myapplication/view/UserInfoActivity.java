package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.CVRecycleViewAdapter;
import com.example.myapplication.databinding.ActivityUserInfoBinding;
import com.example.myapplication.models.CV;
import com.example.myapplication.models.User;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    public static String STR_PARAM = "user";
    private User user;
    ActivityUserInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra(STR_PARAM) != null) {
            user = new Gson().fromJson(getIntent().getStringExtra(STR_PARAM), User.class);
        }

        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.nameEt.setText( user.getName());
        binding.emailEt.setText(user.getEmail());
        binding.selectDate.setText(user.getDate_of_birth().substring(0,10));
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource( binding.getRoot().getContext(), R.array.gender_string_in_edit, R.layout.list_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.selectGender.setAdapter(genderAdapter);
        for (int i = 0; i < genderAdapter.getCount(); i++) {
            if (Objects.equals(user.getGender(), genderAdapter.getItem(i).toString())) {
                binding.selectGender.setSelection(i);
                break;
            }
        }
        ApiService.apiService.getCvByUserId(user.getId()).enqueue(new Callback<List<CV>>() {
            @Override
            public void onResponse(Call<List<CV>> call, Response<List<CV>> response) {
                if (response.isSuccessful()) {
                    List<CV> list = response.body();
                    CVRecycleViewAdapter adapter = new CVRecycleViewAdapter(list, new CVRecycleViewAdapter.OnClickListener() {
                        @Override
                        public void onClick(View view, int pos) {
                            String url = ApiService.url + "/cv/my-cv?id=" + list.get(pos).getId();
                            Intent intent = new Intent(UserInfoActivity.this, PdfViewActivity.class);
                            intent.putExtra(PdfViewActivity.STR_BUNDLE, url);
                            startActivity(intent);
                        }
                    });
                    adapter.hideDeleteBtn = true;
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this, LinearLayoutManager.VERTICAL, false));

                    binding.recyclerView.setAdapter(adapter);
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<CV>> call, Throwable throwable) {
                Toast.makeText(UserInfoActivity.this, "Fails on call API", Toast.LENGTH_LONG).show();
            }
        });
        setContentView(binding.getRoot());
    }
}