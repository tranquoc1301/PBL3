package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.controller.CompanyRecycleViewAdapter;
import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.CompanyAdapter;
import com.example.myapplication.controller.JobAdapter;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.Company;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    List<Job> jobList;

    List<Company> companyList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getJobListFromAPI();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.latestJobViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView5, new AllJobFragment());
                fragmentTransaction.commit();
            }
        });


        return binding.getRoot();
    }
    public void getJobListFromAPI() {
        ApiService.apiService.getJobList().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful()) {
                    jobList = response.body();
                    getCompanyListFromApi();
                    setMainView();
                }
                else {
                    Toast.makeText(HomeFragment.this.getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable throwable) {
                    Toast.makeText(HomeFragment.this.getContext(), "False on call API", Toast.LENGTH_LONG).show();
            }
        });



    }
    public void setMainView() {
        JobAdapter rvAdapter = new JobAdapter(jobList.subList(0, 5), new JobAdapter.OnRecycleViewClickListener() {
            @Override
            public void onRecycleViewClickListener(View v, int position) {
                        Bundle bundle = new Bundle();
                        String jsonJob = new Gson().toJson(jobList.subList(0, 5).get(position));
                        String jsonUser = HomeFragment.this.getActivity().getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
                        bundle.putString("jsonUser", jsonUser);
                        bundle.putString("jsonJob", jsonJob);
                        Intent intent = new Intent(HomeFragment.this.getContext(), JobInfoActivity.class);
                        intent.putExtra("Bundle", bundle);
                        startActivity(intent);
                    }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(HomeFragment.this.getContext(), RecyclerView.VERTICAL, false);
        binding.latestJobRecyclerview.setLayoutManager(layoutManager2);
        binding.latestJobRecyclerview.setAdapter(rvAdapter);

        LinearLayoutManager hotJobRvManager = new LinearLayoutManager(HomeFragment.this.getContext(), RecyclerView.HORIZONTAL, false);
        binding.hotJobRecyclerview.setLayoutManager(hotJobRvManager);
        binding.hotJobRecyclerview.setAdapter(rvAdapter);


//        binding.recyclerView.setLayoutManager(layoutManager);
//        binding.recyclerView.setAdapter(rvAdapter);
    }

    void getCompanyListFromApi() {
        ApiService.apiService.getCompanyList().enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful()) {

                    companyList = response.body();
                    for (int i = 0; i < companyList.size(); i++) {
                        for (int j = 0; j < jobList.size(); j++) {
                            if (jobList.get(j).getCompanyId() == companyList.get(i).getId()){
                                companyList.get(i).setCompanyLogo(jobList.get(j).getSourcePicture());
                                break;
                            }
                        }
                    }

                    List<Company> companies = new ArrayList<>();

                    for (int i = 0; i < 5; i++) {
                        int temp = new Random().nextInt(companyList.size());
                        companies.add(companyList.get(temp));
                    }

                    CompanyAdapter companyAdapter = new CompanyAdapter(companies, binding.companyViewPager, new JobAdapter.OnRecycleViewClickListener() {
                        @Override
                        public void onRecycleViewClickListener(View v, int position) {
                            //can them vao
                            Company company = companies.get(position);
                            String jsonCompany = new Gson().toJson(company);
                            Bundle bundle = new Bundle();
                            bundle.putString("jsonCompany", jsonCompany);
                            String jsonUser = HomeFragment.this.getActivity().getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
                            bundle.putString("jsonUser", jsonUser);
                            Intent intent = new Intent(getContext(), CompanyInfoActivity.class);
                            intent.putExtra("Bundle", bundle);
                            startActivity(intent);
                        }
                    });


                    binding.companyViewPager.setAdapter(companyAdapter);
                    ((CompanyAdapter)binding.companyViewPager.getAdapter()).setLoopList();
                    binding.Indicator.setViewPager(binding.companyViewPager);
                    binding.companyViewPager.setCurrentItem(1);

                    binding.companyViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageScrollStateChanged(int state) {
                            super.onPageScrollStateChanged(state);
                            if (state == ViewPager2.SCROLL_STATE_IDLE || state == ViewPager2.SCROLL_STATE_DRAGGING) {
                                if (binding.companyViewPager.getCurrentItem() == 0)
                                    binding.companyViewPager.setCurrentItem( binding.companyViewPager.getAdapter().getItemCount() - 2, false);
                                else if (binding.companyViewPager.getCurrentItem() == binding.companyViewPager.getAdapter().getItemCount() - 1)
                                    binding.companyViewPager.setCurrentItem( 1,false);
                            }
                        }
                    });


                    companies.clear();
                    for (int i = 0; i < 5; i++) {
                        int temp = new Random().nextInt(companyList.size());
                        companies.add(companyList.get(temp));
                    }

                    CompanyRecycleViewAdapter companyRecycleViewAdapter = new CompanyRecycleViewAdapter(companies, new CompanyRecycleViewAdapter.OnRecycleViewOnClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {
                            Company company = companies.get(position);
                            String jsonCompany = new Gson().toJson(company);
                            Bundle bundle = new Bundle();
                            bundle.putString("jsonCompany", jsonCompany);
                            String jsonUser = HomeFragment.this.getActivity().getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
                            bundle.putString("jsonUser", jsonUser);
                            Intent intent = new Intent(getContext(), CompanyInfoActivity.class);
                            intent.putExtra("Bundle", bundle);
                            startActivity(intent);
                        }
                    });

                    binding.recyclerView.setAdapter(companyRecycleViewAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


                }
                else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}