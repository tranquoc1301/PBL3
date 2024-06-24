package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.ScreenSlidePagerAdapter;
import com.example.myapplication.databinding.ActivityJobInfoBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UserJob;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobInfoActivity extends AppCompatActivity {

    private ActivityJobInfoBinding binding;

    public static Boolean isAdminUse = false;

    private int applyButtonStatus = APPLY;

    public static final int APPLY = 0;

    public static final int CANCEL = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityJobInfoBinding.inflate(getLayoutInflater());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobInfoActivity.super.getOnBackPressedDispatcher().onBackPressed();
            }
        });

        String jsonJob = getIntent().getBundleExtra("Bundle").getString("jsonJob");


        Job job = new Gson().fromJson(jsonJob, Job.class);

        binding.jobName.setText(job.getJobName());
        Picasso.get().load(job.getSourcePicture()).resize(2048, 1600).onlyScaleDown().into(binding.companyLogo);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

        JobTabFragment jobTabFragment = new JobTabFragment(job);

        fragmentList.add(jobTabFragment);

        ApiService.apiService.getCompanyById(job.getCompanyId()).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Company company = response.body();
                        fragmentList.add(CompanyTabFragment.newInstance(company));
                        if (isAdminUse) {
                            fragmentList.add(AllApplyFragment.newInstance(job.getJobId(), null));
                        }
                        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(JobInfoActivity.this, fragmentList);
                        binding.viewPager.setAdapter(adapter);
                        String[] tabText = new String[3];
                        tabText[0] = "Job Details";
                        tabText[1] = "Company";
                        tabText[2] = "Apply";
                        new TabLayoutMediator(binding.tabLayout, binding.viewPager, ((tab, position) -> {
                            tab.setText(tabText[position]);
                        })).attach();
                    }

                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable throwable) {
                Toast.makeText(binding.getRoot().getContext(), "something wrong", Toast.LENGTH_LONG).show();
            }
        });

        LocalDate now = LocalDate.now(ZoneId.of("VST"));
        LocalDate thisJobPost = LocalDate.parse(job.getPostedDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Period difference = Period.between(thisJobPost, now);

        if (difference.getDays() == 0) {
            binding.timeUploadTv.setText("Today");
        }
        else binding.timeUploadTv.setText(difference.getDays() + " day ago");

        String jsonUser = getIntent().getBundleExtra("Bundle").getString("jsonUser");
        User user = new Gson().fromJson(jsonUser, User.class);
        ApiService.apiService.getUserJob(user.getId(), job.getJobId()).enqueue(new Callback<List<UserJob>>() {
            @Override
            public void onResponse(Call<List<UserJob>> call, Response<List<UserJob>> response) {
                if (response.isSuccessful()) {
                    List<UserJob> list = response.body();
                    if (list != null && list.size() > 0) {
                        binding.applyBtn.setText("Cancel");
                        binding.applyBtn.setBackgroundResource(R.drawable.red_btn_custom);
                        applyButtonStatus = CANCEL;
                    }
                    binding.applyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (applyButtonStatus) {
                                case APPLY:
                                    ApiService.apiService.createUserJob(user.getId(), job.getJobId()).enqueue(new Callback<Message>() {
                                        @Override
                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                            if (!response.isSuccessful()) {
                                                Message mes = new Gson().fromJson(response.errorBody().charStream(), Message.class);
                                                Toast.makeText(binding.getRoot().getContext(), mes.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                binding.applyBtn.setText("Cancel");
                                                binding.applyBtn.setBackgroundResource(R.drawable.red_btn_custom);
                                                applyButtonStatus = CANCEL;
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Message> call, Throwable throwable) {

                                            Toast.makeText(binding.getRoot().getContext(), "Fail on call API", Toast.LENGTH_LONG).show();
                                        }

                                    });

                                    break;
                                case CANCEL:
                                    ApiService.apiService.getUserJob(user.getId(), job.getJobId()).enqueue(new Callback<List<UserJob>>() {
                                        @Override
                                        public void onResponse(Call<List<UserJob>> call, Response<List<UserJob>> response) {
                                            if (response.isSuccessful()) {

                                                ApiService.apiService.deleteUserJob(response.body().get(0).getId()).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (!response.isSuccessful()) {
                                                            Message mes = new Gson().fromJson(response.errorBody().charStream(), Message.class);
                                                            Toast.makeText(binding.getRoot().getContext(), mes.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                        else {
                                                            binding.applyBtn.setText("Apply");
                                                            binding.applyBtn.setBackgroundResource(R.drawable.custom_btn);
                                                            applyButtonStatus = APPLY;
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable throwable) {
                                                        Toast.makeText(binding.getRoot().getContext(), "Fail on call API", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                            else {
                                                Message mes = new Gson().fromJson(response.errorBody().charStream(), Message.class);
                                                Toast.makeText(binding.getRoot().getContext(), mes.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<UserJob>> call, Throwable throwable) {
                                            Toast.makeText(binding.getRoot().getContext(), "Fail on call API", Toast.LENGTH_LONG).show();
                                        }
                                    });


                                    break;
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<UserJob>> call, Throwable throwable) {
                Toast.makeText(binding.getRoot().getContext(), "Fail on call API", Toast.LENGTH_LONG).show();
            }
        });


        setContentView(binding.getRoot());
    }
}