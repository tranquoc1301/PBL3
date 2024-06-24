package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.myapplication.controller.ItemTouchHelperListener;
import com.example.myapplication.controller.UserRecycleViewAdapter;
import com.example.myapplication.controller.UserRecycleViewItemTouchHelper;
import com.example.myapplication.databinding.FragmentAdminUserBinding;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminUserFragment extends Fragment {

    FragmentAdminUserBinding binding;

    int page = 1;
    public AdminUserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminUserBinding.inflate(inflater, container, false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        ((UserRecycleViewAdapter)recyclerView.getAdapter()).insertPage(page);
                        page++;
                    }
                }
            }
        });

        ApiService.apiService.getListUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    if (userList != null) {
                        UserRecycleViewAdapter adapter = new UserRecycleViewAdapter(userList);
                        binding.recyclerView.setAdapter(adapter);
                        ItemTouchHelper.SimpleCallback simpleCallback = new UserRecycleViewItemTouchHelper(0, ItemTouchHelper.LEFT, new ItemTouchHelperListener() {
                            @Override
                            public void onSwiped(RecyclerView.ViewHolder viewHolder) {
                                DialogFragment dialogFragment = new DialogFragment();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Are you sure you want to delete?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                int index = viewHolder.getAdapterPosition();
                                                int userId = adapter.getUserId(index);
                                                if (userId == ((MainActivity)getActivity()).currentUserIdLogin) {
                                                    Toast.makeText(getContext(), "Cant delete current user login", Toast.LENGTH_LONG).show();
                                                    adapter.notifyDataSetChanged();
                                                    return;
                                                }
                                                ApiService.apiService.deleteUser(userId).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful()) {
                                                            adapter.deleteItem(index);
                                                        }
                                                        else {
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable throwable) {
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerView);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {

            }
        });

        return binding.getRoot();
    }
    public void changeUserList(List<User> newList) {
        ((UserRecycleViewAdapter)binding.recyclerView.getAdapter()).changeUserList(newList);
    }
}