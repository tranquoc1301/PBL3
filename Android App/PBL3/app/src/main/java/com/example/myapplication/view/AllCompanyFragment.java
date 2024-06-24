package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.controller.CompanyRecycleViewAdapter;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.FragmentAllCompanyBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllCompanyFragment extends Fragment {

    List<Company> companyList;
    List<Company> companiesInAdapter = new ArrayList<>();
    FragmentAllCompanyBinding binding;





    public AllCompanyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllCompanyBinding.inflate(inflater, container, false);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {

                        int currentPage = ((CompanyRecycleViewAdapter)recyclerView.getAdapter()).getPage();
                        int itemInsert;
                        if (companyList.size() - companiesInAdapter.size() <= 0) return;
                        if ( companyList.size() - companiesInAdapter.size() <= 20) {
                            itemInsert = companyList.size() - companiesInAdapter.size();
                        }
                        else itemInsert = 20;
                        companiesInAdapter.addAll(companyList.subList(currentPage * 20, currentPage * 20 + itemInsert));
                        recyclerView.getAdapter().notifyItemRangeInserted(companiesInAdapter.size(), 20);
                        ((CompanyRecycleViewAdapter)recyclerView.getAdapter()).insertPage();
                    }

                }
            }
        });

        getCompanyListFromAdapter();

        return binding.getRoot();
    }

    void getCompanyListFromAdapter() {
        ApiService.apiService.getCompanyList().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {
                    companyList = response.body();
                    ApiService.apiService.getJobList().enqueue(new Callback<List<Job>>() {
                        @Override
                        public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                            if (response.isSuccessful()) {
                                List<Job> jobList = response.body();
                                for (int i = 0; i < jobList.size(); i++) {
                                    jobList.get(i).findCompanyById(companyList, jobList.get(i).getCompanyId());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Job>> call, Throwable throwable) {
                        }
                    });
                    companiesInAdapter.addAll(companyList.subList(0,20));
                    CompanyRecycleViewAdapter companyAdapter = new CompanyRecycleViewAdapter(companiesInAdapter, new CompanyRecycleViewAdapter.OnRecycleViewOnClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {
                            Company company = companiesInAdapter.get(position);
                            String jsonCompany = new Gson().toJson(company);
                            Bundle bundle = new Bundle();
                            bundle.putString("jsonCompany", jsonCompany);
                            String jsonUser = AllCompanyFragment.this.getActivity().getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
                            bundle.putString("jsonUser", jsonUser);
                            Intent intent = new Intent(getContext(), CompanyInfoActivity.class);
                            intent.putExtra("Bundle", bundle);
                            startActivity(intent);
                        }
                    });

                    companyAdapter.insertPage();

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    binding.recyclerView.setAdapter(companyAdapter);


                }
                else  {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changeCompanyList(List<Company> newList) {
        ((CompanyRecycleViewAdapter)binding.recyclerView.getAdapter()).changeCompanyList(newList);
    }



}