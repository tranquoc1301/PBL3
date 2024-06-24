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
import com.example.myapplication.controller.UserRecycleViewAdapter;
import com.example.myapplication.databinding.FragmentAllApplyBinding;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllApplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllApplyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int jobId;
    private String mParam2;

    private FragmentAllApplyBinding binding;

    public AllApplyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllApplyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllApplyFragment newInstance(int param1, String param2) {
        AllApplyFragment fragment = new AllApplyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jobId = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllApplyBinding.inflate(inflater, container, false);
        ApiService.apiService.getApplyOfJobList(jobId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    UserRecycleViewAdapter adapter = new UserRecycleViewAdapter(userList, new UserRecycleViewAdapter.OnRecycleViewOnClickListener() {
                        @Override
                        public void onClick(View view, int pos) {
                            String jsonUser = new Gson().toJson(userList.get(pos));
                            Intent intent = new Intent(getContext(), UserInfoActivity.class);
                            intent.putExtra(UserInfoActivity.STR_PARAM, jsonUser);
                            startActivity(intent);

                        }
                    });

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    binding.recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {

            }
        });
        return binding.getRoot();
    }
}