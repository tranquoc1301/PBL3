package com.example.myapplication.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.companyViewHolder> {
    List<Company> companyList;

    ViewPager2 viewPager2;

    JobAdapter.OnRecycleViewClickListener onRecycleViewClickListener;

    public CompanyAdapter(List<Company> companyList, ViewPager2 viewPager2, JobAdapter.OnRecycleViewClickListener onRecycleViewClickListener) {
        this.companyList = companyList;
        this.viewPager2 = viewPager2;
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    @NonNull
    @Override
    public companyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_pager, parent, false);
        return new companyViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull companyViewHolder holder, int position) {
            if (!companyList.isEmpty()) {
                Company company = companyList.get(position);
                holder.name.setText(company.getCompanyName());
                holder.infomation.setText(company.getCompanyDescription());
                if (company.getCompanyLogo() != null && !company.getCompanyLogo().isEmpty()) {
                    Picasso.get().load(company.getCompanyLogo()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).resize(2048,1600).onlyScaleDown().into(holder.logoView);
                }

                ApiService.apiService.getJobListByCompany(company.getId()).enqueue(new Callback<List<Job>>() {
                    @Override
                    public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                        if (response.isSuccessful()) {
                            List<Job> jobList = response.body();
                            String temp;
                            if (jobList.size() == 1) {
                                temp = jobList.size() + " job";
                            }
                            else {
                                temp = jobList.size() + " jobs";
                            }
                            holder.numJobs.setText(temp);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Job>> call, Throwable throwable) {

                    }
                });
            }
            else {
                Log.d("check", "EMPTY");
            }

        }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class companyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView logoView;
        ImageView imageView;
        TextView infomation;
        TextView name;
        TextView numJobs;
        public companyViewHolder(@NonNull View itemView) {
            super(itemView);
            logoView = itemView.findViewById(R.id.company_logo);
            imageView = itemView.findViewById(R.id.company_image);
            infomation = itemView.findViewById(R.id.company_intro);
            name = itemView.findViewById(R.id.company_name);
            numJobs = itemView.findViewById(R.id.num_jobs);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onRecycleViewClickListener.onRecycleViewClickListener(view, this.getLayoutPosition());
        }
    }


    public void setLoopList() {
        this.companyList.add(0, companyList.get(companyList.size() - 1));
        this.companyList.add(companyList.get(0));
    }

}
