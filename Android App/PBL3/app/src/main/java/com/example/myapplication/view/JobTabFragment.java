package com.example.myapplication.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentJobTabBinding;
import com.example.myapplication.models.Job;

public class JobTabFragment extends Fragment {

    private Job job;

    private FragmentJobTabBinding binding;
    public JobTabFragment() {
        // Required empty public constructor
    }


    public JobTabFragment(Job job) {
        this.job = job;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJobTabBinding.inflate(inflater, container, false);

        binding.locationTv.setText(job.getLocation());

        binding.rankTv.setText(job.getRole());

        binding.jobDescriptionTv.setText(job.getJobDescription());

        binding.benefitsTv.setText(job.getBenefits());

        binding.expTv.setText(job.getExperienceRequirement());

        binding.jobRequirementTv.setText(job.getJobRequirement());

        binding.recruitsTv.setText(job.getNumberOfRecruitment());

        binding.salaryTv.setText((job.getSalary()));

        binding.sexTv.setText(job.getGenderRequirement());

        binding.jobTv.setText(job.getIndustry());


        return binding.getRoot();
    }
}