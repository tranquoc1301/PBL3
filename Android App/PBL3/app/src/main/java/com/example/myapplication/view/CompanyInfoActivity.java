package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.JobAdapter;
import com.example.myapplication.databinding.ActivityCompanyInfoBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyInfoActivity extends AppCompatActivity {

    ActivityCompanyInfoBinding binding;

    Company company;

    List<Job> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompanyInfoBinding.inflate(getLayoutInflater());

        String jsonCompany = "";
        if (this.getIntent().getBundleExtra("Bundle") != null) {
            jsonCompany = this.getIntent().getBundleExtra("Bundle").getString("jsonCompany");
        }

        this.company = new Gson().fromJson(jsonCompany, Company.class);

        binding.companyName.setText(company.getCompanyName());
        Picasso.get().load(company.getCompanyLogo()).resize(640, 480).onlyScaleDown().into(binding.companyLogo);
        binding.locationTv.setText(company.getLocation());
        binding.staffSizeTv.setText(company.getStaffSize());
        binding.companyScrip.setText(company.getCompanyDescription());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyInfoActivity.this.getOnBackPressedDispatcher().onBackPressed();
            }
        });

        getJobListByCompanyIdFromApi();

        setContentView(binding.getRoot());
    }

    public void getJobListByCompanyIdFromApi() {
        ApiService.apiService.getJobListByCompany(this.company.getId()).enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful()) {
                    jobList = response.body();
                    JobAdapter jobAdapter = new JobAdapter(jobList, new JobAdapter.OnRecycleViewClickListener() {
                        @Override
                        public void onRecycleViewClickListener(View v, int position) {
                            Bundle bundle = new Bundle();
                            String jsonJob = new Gson().toJson(jobList.get(position));
                            bundle.putString("jsonJob", jsonJob);
                            Intent intent = new Intent(binding.getRoot().getContext(), JobInfoActivity.class);
                            String jsonUser = CompanyInfoActivity.this.getIntent().getExtras().getBundle("Bundle").getString("jsonUser");
                            bundle.putString("jsonUser", jsonUser);
                            intent.putExtra("Bundle", bundle);
                            startActivity(intent);
                        }
                    });
                    binding.recyclerView.setAdapter(jobAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false));
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable throwable) {

            }
        });
    }
}