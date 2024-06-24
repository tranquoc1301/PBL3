package com.example.myapplication.controller;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.CompanyInfomationBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Message;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRecycleViewAdapter extends RecyclerView.Adapter<CompanyRecycleViewAdapter.CompanyRecycleViewHolder> {

    private int PAGE = 0;

    List<Company> companyList;

    OnRecycleViewOnClickListener recycleViewOnClickListener;

    public void insertPage() {
        this.PAGE++;
    }

    public int getPage() {
        return PAGE;
    }

    public CompanyRecycleViewAdapter(List<Company> companyList, OnRecycleViewOnClickListener recycleViewOnClickListener) {
        this.companyList = companyList;
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    @NonNull
    @Override
    public CompanyRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyRecycleViewHolder(CompanyInfomationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyRecycleViewHolder holder, int position) {
        holder.binding.companyName.setText(companyList.get(position).getCompanyName());
        if (companyList.get(position).getCompanyLogo() != null && !Objects.equals(companyList.get(position).getCompanyLogo(), "") ) {
            Picasso.get().load(companyList.get(position).getCompanyLogo()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).resize(640,480).onlyScaleDown().into(holder.binding.companyLogo);
        }

        holder.binding.locationTv.setText(companyList.get(position).getLocation());
        holder.binding.staffSizeTv.setText(companyList.get(position).getStaffSize());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public interface OnRecycleViewOnClickListener {
        public void onClickListener(View view, int position);
    }



    public class CompanyRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CompanyInfomationBinding binding;

        public CompanyRecycleViewHolder(CompanyInfomationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recycleViewOnClickListener.onClickListener(view, getLayoutPosition());
        }
    }


    public void changeCompanyList(List<Company> newList) {
        this.companyList.clear();
        this.companyList.addAll(newList);
        notifyDataSetChanged();
    }

    public  void deleteCompany(int index) {
        this.companyList.remove(index);
        notifyItemRemoved(index);
    }

    public int getCompanyId(int index) {
        return this.companyList.get(index).getId();
    }

}
