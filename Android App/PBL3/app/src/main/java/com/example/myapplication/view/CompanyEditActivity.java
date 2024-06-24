package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.ActivityCompanyEditBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.PostCompany;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyEditActivity extends AppCompatActivity {

    ActivityCompanyEditBinding binding;

    boolean useToAdd = false;

    public void usedToAdd() {
        this.useToAdd = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsonCompany = null;
        binding = ActivityCompanyEditBinding.inflate(getLayoutInflater());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        if (getIntent().getBundleExtra("Bundle") != null) {
            jsonCompany = getIntent().getBundleExtra("Bundle").getString("jsonCompany");
        }

        if (getIntent().getExtras() != null) {
            this.useToAdd = getIntent().getExtras().getBoolean("usedToAdd");
            binding.okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = binding.companyName.getText().toString();
                    String location = binding.companyLocation.getText().toString();
                    String staffSize = binding.staffSizeTv.getText().toString();
                    String description = binding.companyDcp.getText().toString();
                    if (name.equals("") || location.equals("") || staffSize.equals("") || description.equals("")) {
                        Toast.makeText(binding.getRoot().getContext(), "Some values are empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    PostCompany postCompany = new PostCompany(name, location, staffSize, description);
                    ApiService.apiService.createCompany(postCompany).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(binding.getRoot().getContext(), "Create Success", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(binding.getRoot().getContext(), "Company already exists", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {
                            Toast.makeText(binding.getRoot().getContext(), "Fails on call API", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }



        if (jsonCompany != null) {
            Company company = new Gson().fromJson(jsonCompany, Company.class);
            binding.companyName.setText(company.getCompanyName());
            binding.companyDcp.setText(company.getCompanyDescription());
            binding.companyLocation.setText(company.getLocation());
            binding.staffSizeTv.setText(company.getStaffSize());
            binding.okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        String name = binding.companyName.getText().toString();
                        String location = binding.companyLocation.getText().toString();
                        String staffSize = binding.staffSizeTv.getText().toString();
                        String description = binding.companyDcp.getText().toString();
                        if (name.equals("") || location.equals("") || staffSize.equals("") || description.equals("")) {
                            Toast.makeText(binding.getRoot().getContext(), "Some values are empty", Toast.LENGTH_LONG).show();
                            return;
                        }
                        PostCompany postCompany = new PostCompany(name, location, staffSize, description);

                        ApiService.apiService.updateCompany(company.getId(), postCompany).enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                if (response.isSuccessful()) {
                                    Message mes = response.body();
                                    Toast.makeText(binding.getRoot().getContext(), "Update success", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable throwable) {
                                Toast.makeText(binding.getRoot().getContext(), "Fail on call API", Toast.LENGTH_LONG).show();
                            }
                        });

                }
            });
        }
        setContentView(binding.getRoot());
    }
}