package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.JobAdapter;
import com.example.myapplication.databinding.FragmentCompanyTabBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyTabFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COMPANY = "ARG_COMPANY";

    // TODO: Rename and change types of parameters
    private Company mCompany;

    private FragmentCompanyTabBinding binding;

    public CompanyTabFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CompanyTabFragment newInstance(Company company) {
        CompanyTabFragment fragment = new CompanyTabFragment();
        Bundle args = new Bundle();
        String param = new Gson().toJson(company);
        args.putString(ARG_COMPANY, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCompany = new Gson().fromJson(getArguments().getString(ARG_COMPANY), Company.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompanyTabBinding.inflate(inflater, container, false);

        binding.companyName.setText(mCompany.getCompanyName());
        binding.staffSizeTv.setText(mCompany.getStaffSize());
        binding.locationTv.setText(mCompany.getLocation());
        Picasso.get().load(mCompany.getCompanyLogo()).resize(640, 480).onlyScaleDown().into(binding.companyLogo);
        binding.companyScrip.setText(mCompany.getCompanyDescription());
        loadRecycleView();

        return binding.getRoot();
    }

    public void loadRecycleView() {
        ApiService.apiService.getJobListByCompany(mCompany.getId()).enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        JobAdapter adapter = new JobAdapter(response.body(), new JobAdapter.OnRecycleViewClickListener() {
                            @Override
                            public void onRecycleViewClickListener(View v, int position)  {
                                Bundle bundle = new Bundle();
                                String jsonJob = new Gson().toJson(response.body().get(position));
                                bundle.putString("jsonJob", jsonJob);
                                Intent intent = new Intent(binding.getRoot().getContext(), JobInfoActivity.class);
                                intent.putExtra("jobBundle", bundle);

                                startActivity(intent);
                            }
                        });
                        binding.recyclerView.setAdapter(adapter);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), RecyclerView.HORIZONTAL, false));

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable throwable) {

            }
        });
    }
}