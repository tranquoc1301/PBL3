package com.example.myapplication.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.FragmentRegisterBinding;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.PostUser;
import com.google.gson.Gson;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    FragmentRegisterBinding binding;

    private ViewPager2 viewPager;

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        binding.selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        binding.selectDate.setText(i2 + "-" + i1 + "-" + i);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource( binding.getRoot().getContext(), R.array.gender_string, R.layout.list_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.selectGender.setAdapter(genderAdapter);
        binding.registerBtn.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        String name = binding.nameEt.getText().toString();
        String email = binding.emailEt.getText().toString();
        String password = binding.passwordEt.getText().toString();
        String retypePassword = binding.retypePassword.getText().toString();
        String dob = binding.selectDate.getText().toString();
        String gender = binding.selectGender.getSelectedItem().toString();

        if (name.equals("")) {
            binding.errorTv.setText("name is empty");
            return;
        }
        if (email.equals("")) {
            binding.errorTv.setText("email is empty");
            return;
        }

        if (password.equals("")) {
            binding.errorTv.setText("password is empty");
            return;
        }

        if (retypePassword.equals("")) {
            binding.errorTv.setText("retype password is empty");
            return;
        }

        if (dob.equals("")) {
            binding.errorTv.setText("date of birth is empty");
            return;
        }

        if (gender.equals("")) {
            binding.errorTv.setText("gender is empty");
            return;
        }

        PostUser user = new PostUser(name, email, password, retypePassword, dob, gender);


        ApiService.apiService.postRegister(user).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.isSuccessful()) {
                    Message message = new Gson().fromJson(response.errorBody().charStream(), Message.class);
                    if (message.getMessage() == null) {
                        binding.errorTv.setText(message.getError().get(0).getMsg());
                    }
                    else {
                        binding.errorTv.setText(message.getMessage());
                    }
                }
                else {
                    binding.nameEt.setText("");
                    binding.emailEt.setText("");
                    binding.passwordEt.setText("");
                    binding.retypePassword.setText("");
                    binding.selectDate.setText("");
                    binding.selectGender.setSelection(0);
                    Toast.makeText(binding.getRoot().getContext(), "Register Success", Toast.LENGTH_LONG).show();
                    viewPager.setCurrentItem( viewPager.getCurrentItem()-1, true);
                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                Toast.makeText(binding.getRoot().getContext(), "Error when call api", Toast.LENGTH_SHORT).show();
                Log.println(Log.ERROR, "API error", throwable.getMessage());
            }
        });
    }
}