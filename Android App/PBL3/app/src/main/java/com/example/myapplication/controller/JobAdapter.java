package com.example.myapplication.controller;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.JobInfomationBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.squareup.picasso.Picasso;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobNormalViewHolder> {

    List<Job> jobList;

    public interface OnRecycleViewClickListener {
        public void onRecycleViewClickListener(View v, int position);
    }

    private final OnRecycleViewClickListener onRecycleViewClickListener;

    @NonNull
    @Override
    public JobNormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JobInfomationBinding binding = JobInfomationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JobNormalViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JobNormalViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.viewBinding.jobName.setText(job.getJobName());
        if (!Objects.equals(job.getSourcePicture(), "")) {
            Picasso.get().load(job.getSourcePicture()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).resize(2048, 1600).onlyScaleDown().into(holder.viewBinding.companyLogo);
        }
        holder.viewBinding.jobLocation.setText(job.getLocation());
        holder.viewBinding.salaryTv.setText(job.getSalary());

        ApiService.apiService.getCompanyById(job.getCompanyId()).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Company company = response.body();
                    holder.viewBinding.companyName.setText(company.getCompanyName());
                }
            }
            @Override
            public void onFailure(Call<Company> call, Throwable throwable) {
                Log.println(Log.DEBUG, "asdfasdfasdfasdfasdfasdfasdf", throwable.getMessage());
            }
        });




        LocalDate now = LocalDate.now(ZoneId.of("VST"));
        LocalDate thisJobPost = null;
        try {
            thisJobPost = LocalDate.parse(job.getPostedDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        catch (DateTimeParseException e) {
            thisJobPost = LocalDate.parse(job.getPostedDate().substring(0,10), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        Period difference = Period.between(thisJobPost, now);

        if (difference.getDays() == 0) {
            holder.viewBinding.timeUploadTv.setText("Today");
        }
        else holder.viewBinding.timeUploadTv.setText(difference.getDays() + " day ago");

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class JobNormalViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public JobInfomationBinding viewBinding;

        public JobNormalViewHolder(@NonNull JobInfomationBinding binding) {
            super(binding.getRoot());
            this.viewBinding = binding;
            viewBinding.getRoot().setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onRecycleViewClickListener.onRecycleViewClickListener(view, this.getLayoutPosition());
        }
    }

    public JobAdapter(List<Job> jobList, OnRecycleViewClickListener onRecycleViewClickListener) {
        this.jobList = jobList;
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    public List<Job> getJobList() {
        return  this.jobList;
    }
    public void changeJobList(List<Job> newList) {
        this.jobList.clear();
        this.jobList.addAll(newList);
        notifyDataSetChanged();
    }
    public void deleteJob(int index) {
        this.jobList.remove(index);
        notifyItemRemoved(index);
    }
    public int getJobId(int index) {
        return this.jobList.get(index).getJobId();
    }
}
