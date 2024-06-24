package com.example.myapplication.controller;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.CvViewHolderBinding;
import com.example.myapplication.models.CV;
import com.example.myapplication.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CVRecycleViewAdapter extends RecyclerView.Adapter<CVRecycleViewAdapter.CVViewHolder> {

    private final List<CV> cvList;

    public boolean hideDeleteBtn = false;

    private final OnClickListener onClickListener;

    public CVRecycleViewAdapter(List<CV> list, OnClickListener onClickListener) {
        this.cvList = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CVRecycleViewAdapter.CVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CvViewHolderBinding binding = CvViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CVViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CVViewHolder holder, int position) {
        holder.getBinding().fileName.setText(cvList.get(position).getName());
        if (!hideDeleteBtn) {
            holder.getBinding().deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiService.apiService.deleteCV(cvList.get(holder.getLayoutPosition()).getId()).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.isSuccessful()) {
                                deleteItem(holder.getLayoutPosition());
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {
                            Toast.makeText(view.getContext(), "Fails on call API", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
        else {
            holder.getBinding().deleteBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cvList.size();
    }

    static class CVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final CvViewHolderBinding binding;

        private final OnClickListener onClickListener;

        public CvViewHolderBinding getBinding() {
            return binding;
        }

        public CVViewHolder(@NonNull CvViewHolderBinding binding, OnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view, getLayoutPosition());
        }

    }

    public void deleteItem(int pos) {
        this.cvList.remove(pos);
        notifyItemRemoved(pos);
    }


    public interface OnClickListener {
        public void onClick(View view, int pos);
    }

    public void insertItem(CV cv) {
        this.cvList.add(cv);
        notifyItemInserted(this.cvList.size()-1);
    }
}
