package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.JobAdapter;
import com.example.myapplication.databinding.FragmentApplyBinding;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.UserJob;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApplyFragment extends Fragment {

    public final String USER_ID_ARG = "USER_ID_ARG";

    private int userId;

    private List<Job> jobList;

    FragmentApplyBinding binding;

    public static ApplyFragment newIntent(int id) {
        ApplyFragment applyFragment = new ApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(applyFragment.USER_ID_ARG, id);
        applyFragment.setArguments(bundle);
        return applyFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(USER_ID_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentApplyBinding.inflate(inflater, container, false);

        ApiService.apiService.getApplyList(userId).enqueue(new Callback<List<UserJob>>() {
            @Override
            public void onResponse(Call<List<UserJob>> call, Response<List<UserJob>> response) {
                if (response.isSuccessful()) {
                    List<UserJob> userJobList = response.body();
                    if (userJobList != null) {
                        jobList = new ArrayList<>();
                        for (int i = 0; i < userJobList.size(); i++) {
                            ApiService.apiService.getJobById(userJobList.get(i).getJobId()).enqueue(new Callback<Job>() {
                                @Override
                                public void onResponse(Call<Job> call, Response<Job> response) {
                                    if (response.isSuccessful()) {
                                        jobList.add(response.body());
                                        if (jobList.size() == userJobList.size()) {
                                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                            binding.recyclerView.setAdapter(new JobAdapter(jobList, new JobAdapter.OnRecycleViewClickListener() {
                                                @Override
                                                public void onRecycleViewClickListener(View v, int position) {
                                                    Bundle bundle = new Bundle();
                                                    String jsonJob = new Gson().toJson(jobList.get(position));
                                                    bundle.putString("jsonJob", jsonJob);
                                                    String jsonUser = ApplyFragment.this.getActivity().getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
                                                    bundle.putString("jsonUser", jsonUser);
                                                    Intent intent = new Intent(getContext(), JobInfoActivity.class);
                                                    intent.putExtra("Bundle", bundle);
                                                    startActivity(intent);
                                                }
                                            }));


                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Job> call, Throwable throwable) {

                                }
                            });
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<List<UserJob>> call, Throwable throwable) {

            }
        });

        return binding.getRoot();
    }
}