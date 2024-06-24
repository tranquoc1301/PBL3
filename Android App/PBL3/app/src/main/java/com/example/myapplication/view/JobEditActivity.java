package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.ActivityJobEditBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Industry;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.PostJob;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobEditActivity extends AppCompatActivity {

    ActivityJobEditBinding binding;

    List<Company> companyList;

    Job job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobEditBinding.inflate(getLayoutInflater());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        if (getIntent().getExtras() != null) {
            String jsonJob = getIntent().getStringExtra("jsonJob");
            if (!Objects.equals(jsonJob, "")) {
                job = new Gson().fromJson(jsonJob, Job.class);
                binding.jobName.setText(job.getJobName());
                binding.location.setText(job.getLocation());
                binding.enrollmentLocation.setText(job.getEnrollmentLocation());
                binding.role.setText(job.getRole());
                binding.salaryTv.setText(job.getSalary());
                binding.genderRequirement.setText(job.getGenderRequirement());
                binding.numberOfRecruitment.setText(job.getNumberOfRecruitment());
                binding.experienceRequirement.setText(job.getExperienceRequirement());
                binding.benefits.setText(job.getBenefits());
                binding.jobDcp.setText(job.getJobDescription());
                binding.jobRequirementTv.setText(job.getJobDescription());
                binding.ageRequirement.setText(job.getAgeRequirement());
                binding.industry.setText(job.getIndustry());
                binding.sourcePicture.setText(job.getSourcePicture());
                if (!Objects.equals(job.getSourcePicture(), "")) {
                    Picasso.get().load(job.getSourcePicture()).resize(480, 480).onlyScaleDown().into(binding.avatar);
                }
                setCompanySpinner();
                binding.okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = binding.jobName.getText().toString();
                        String location = binding.location.getText().toString();
                        String enrollmentLocation = binding.enrollmentLocation.getText().toString();
                        String role = binding.role.getText().toString();
                        String salary = binding.salaryTv.getText().toString();
                        String genderRequirement = binding.genderRequirement.getText().toString();
                        String numberOfRecruitment = binding.numberOfRecruitment.getText().toString();
                        String experienceRequirement = binding.experienceRequirement.getText().toString();
                        String benefits = binding.benefits.getText().toString();
                        String jobDcp = binding.jobDcp.getText().toString();
                        String jobRequirement = binding.jobRequirementTv.getText().toString();
                        String ageRequirement = binding.ageRequirement.getText().toString();
                        String industry = binding.industry.getText().toString();
                        String srcPic = binding.sourcePicture.getText().toString();
                        if (name.equals("") || location.equals("") || enrollmentLocation.equals("") || salary.equals("") || genderRequirement.equals("") ||
                                numberOfRecruitment.equals("") || experienceRequirement.equals("") || benefits.equals("") || jobDcp.equals("") || jobRequirement.equals("")
                        || ageRequirement.equals("") || industry.equals("")) {
                            Toast.makeText(binding.getRoot().getContext(), "Values is empty.", Toast.LENGTH_LONG).show();
                        }
                        int CompanyId = companyList.get(binding.companyName.getSelectedItemPosition()).getId();
                        Job updateJob = new Job(job.getJobId(), name, CompanyId, industry, location, job.getPostedDate(), enrollmentLocation, role,
                                salary, genderRequirement, numberOfRecruitment, ageRequirement, job.getProbationTime(), job.getWorkWay(), experienceRequirement,
                                job.getDegreeRequirement(), benefits, jobDcp, jobRequirement, job.getDeadline(), srcPic);
                        ApiService.apiService.updateJob(job.getJobId(), updateJob).enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(binding.getRoot().getContext(), "update job success.", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(binding.getRoot().getContext(), "Cant find any change.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable throwable) {

                            }
                        });
                    }
                });
            }

        }

        else {
            setCompanySpinner();
            binding.sourcePicture.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (textView.getText() != "") {
                        Picasso.get().load(textView.getText().toString()).resize(480,480).onlyScaleDown().into(binding.avatar);
                    }
                    return true;
                }
            });
            binding.okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = binding.jobName.getText().toString();
                    String location = binding.location.getText().toString();
                    String enrollmentLocation = binding.enrollmentLocation.getText().toString();
                    String role = binding.role.getText().toString();
                    String salary = binding.salaryTv.getText().toString();
                    String genderRequirement = binding.genderRequirement.getText().toString();
                    String numberOfRecruitment = binding.numberOfRecruitment.getText().toString();
                    String experienceRequirement = binding.experienceRequirement.getText().toString();
                    String benefits = binding.benefits.getText().toString();
                    String jobDcp = binding.jobDcp.getText().toString();
                    String jobRequirement = binding.jobRequirementTv.getText().toString();
                    String ageRequirement = binding.ageRequirement.getText().toString();
                    String industry = binding.industry.getText().toString();
                    String companyName = binding.companyName.getSelectedItem().toString();
                    String srcPic = binding.sourcePicture.getText().toString();
                    if (name.equals("") || location.equals("") || enrollmentLocation.equals("") || salary.equals("") || genderRequirement.equals("") ||
                            numberOfRecruitment.equals("") || experienceRequirement.equals("") || benefits.equals("") || jobDcp.equals("") || jobRequirement.equals("")
                            || ageRequirement.equals("") || industry.equals("")) {
                        Toast.makeText(binding.getRoot().getContext(), "Values is empty.", Toast.LENGTH_LONG).show();
                    }
                    PostJob newJob = new PostJob(name, companyName, industry, location, enrollmentLocation, role,
                            salary, genderRequirement, numberOfRecruitment, ageRequirement, experienceRequirement,
                            benefits, jobDcp, jobRequirement, srcPic);
                    ApiService.apiService.createJob(newJob).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(binding.getRoot().getContext(), "create job success.", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(binding.getRoot().getContext(), "Cant find any change.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {

                        }
                    });
                }
            });
        }

        setContentView(binding.getRoot());
    }

    public void setCompanySpinner() {
        ApiService.apiService.getCompanyList().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    companyList = response.body();
                    List<String> companyNames = new ArrayList<>();
                    int pos = 0;
                    if (companyList != null) {
                        for (int i = 0; i < companyList.size(); i++) {
                            companyNames.add(companyList.get(i).getCompanyName());
                            if (job != null && Objects.equals(companyList.get(i).getId(), job.getCompanyId())) {
                                pos = i;
                            }
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(binding.getRoot().getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, companyNames);
                    binding.companyName.setAdapter(arrayAdapter);
                    binding.companyName.setSelection(pos);
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable throwable) {

            }
        });
    }


}