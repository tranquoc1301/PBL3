package com.example.myapplication.controller;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.UserInfomationBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRecycleViewAdapter extends RecyclerView.Adapter<UserRecycleViewAdapter.UserViewHolder> {
    List<User> AllUser;
    List<User> UserShowed;



    public OnRecycleViewOnClickListener listener;

    public UserRecycleViewAdapter(List<User> userList) {
        AllUser = userList;
        int firstIndex = 0;
        int lastIndex = 20;
        if (userList.size() < lastIndex) {
            lastIndex = userList.size();
        }
        UserShowed = new ArrayList<>();
        UserShowed.addAll((userList.subList(firstIndex, lastIndex)));
    }

    public UserRecycleViewAdapter(List<User> userList, OnRecycleViewOnClickListener listener) {
        AllUser = userList;
        int firstIndex = 0;
        int lastIndex = 20;
        if (userList.size() < lastIndex) {
            lastIndex = userList.size();
        }
        UserShowed = new ArrayList<>();
        UserShowed.addAll((userList.subList(firstIndex, lastIndex)));
        this.listener = listener;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void insertPage(int page) {

        int firstIndex = page*20;
        if (firstIndex >= AllUser.size()) return;
        int lastIndex = firstIndex+20;
        if (AllUser.size() < lastIndex) {
            lastIndex = AllUser.size();
        }
        UserShowed.addAll(AllUser.subList(firstIndex, lastIndex));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserRecycleViewAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserInfomationBinding binding = UserInfomationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecycleViewAdapter.UserViewHolder holder, int position) {
        User user = AllUser.get(position);
        if (!Objects.equals(user.getAvatar_scr(), "")) {
            Picasso.get().load(user.getAvatar_scr()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).resize(60, 60).onlyScaleDown().into(holder.binding.avatar);
        }
        holder.binding.nameTv.setText(user.getName());
        holder.binding.dobTv.setText(user.getDate_of_birth().substring(0,10));
        holder.binding.genderTv.setText(user.getGender());
    }

    @Override
    public int getItemCount() {
        return UserShowed.size();
    }

    public void deleteItem(int index) {
        UserShowed.remove(index);
        notifyItemRemoved(index);
    }

    public int getUserId(int index) {

        return this.UserShowed.get(index).getId();

    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public UserInfomationBinding binding;
        public UserViewHolder(@NonNull UserInfomationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClick(view, getAdapterPosition());
            }
        }
    }
    public void changeUserList(List<User> newList) {
        this.UserShowed.clear();
        this.UserShowed.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnRecycleViewOnClickListener {
        void onClick(View view, int pos);
    }
}
