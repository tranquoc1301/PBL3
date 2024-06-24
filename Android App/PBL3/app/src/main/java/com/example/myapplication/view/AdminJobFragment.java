package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.CompanyRecycleViewAdapter;
import com.example.myapplication.controller.ItemTouchHelperListener;
import com.example.myapplication.controller.JobAdapter;
import com.example.myapplication.controller.JobRecycleViewItemTouchHelper;
import com.example.myapplication.databinding.FragmentAdminCompanyBinding;
import com.example.myapplication.databinding.FragmentAdminJobBinding;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.Message;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminJobFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentAdminJobBinding binding;

    // TODO: Rename and change types of parameters
    private String jsonJob;

    private List<Job> jobList;

    public AdminJobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminJobFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminJobFragment newInstance(String param1, String param2) {
        AdminJobFragment fragment = new AdminJobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jsonJob = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminJobBinding.inflate(inflater, container, false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        ApiService.apiService.getJobList().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                if (response.isSuccessful()) {
                    jobList = response.body();

                    JobAdapter jobAdapter = new JobAdapter(jobList, new JobAdapter.OnRecycleViewClickListener() {
                        @Override
                        public void onRecycleViewClickListener(View v, int position) {
                            String jsonJob = new Gson().toJson(jobList.get(position));
                            Intent intent = new Intent(getContext(), JobEditActivity.class);
                            intent.putExtra("jsonJob", jsonJob);
                            startActivity(intent);
                        }
                    });
                    binding.recyclerView.setAdapter(jobAdapter);
                    ItemTouchHelper.SimpleCallback simpleCallback = new JobRecycleViewItemTouchHelper(0, ItemTouchHelper.LEFT, new ItemTouchHelperListener() {
                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder) {
                            if (viewHolder instanceof JobAdapter.JobNormalViewHolder) {
                                DialogFragment dialogFragment = new DialogFragment();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Are you sure you want to delete?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                int index = viewHolder.getAdapterPosition();
                                                int jobId = jobAdapter.getJobId(index);
                                                ApiService.apiService.deleteJob(jobId).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful()) {
                                                            jobAdapter.deleteJob(index);
                                                        }
                                                        else {
                                                            Toast.makeText(getContext(), "fail on deletejob", Toast.LENGTH_LONG).show();
                                                            jobAdapter.notifyDataSetChanged();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable throwable) {
                                                        Toast.makeText(getContext(), "fail on call api", Toast.LENGTH_LONG).show();
                                                        jobAdapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                jobAdapter.notifyDataSetChanged();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    });
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerView);
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable throwable) {

            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JobEditActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
    public void changeJobList(List<Job> newList) {
        ((JobAdapter)binding.recyclerView.getAdapter()).changeJobList(newList);
    }
}