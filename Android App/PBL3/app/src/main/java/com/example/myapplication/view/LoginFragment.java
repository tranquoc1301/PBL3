package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.PostUser;
import com.example.myapplication.models.User;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding loginFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater, container, false);

        loginFragmentBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginFragmentBinding.emailEt.getText().toString();
                String password = loginFragmentBinding.passwordEt.getText().toString();

                PostUser user = new PostUser(email, password);
                ApiService.apiService.postLogin(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            Message message = new Gson().fromJson(response.errorBody().charStream(), Message.class);
                            if (message.getMessage() != null) {
                                loginFragmentBinding.errorTv.setText(message.getMessage());
                            }
                            else {
                                loginFragmentBinding.errorTv.setText(message.getError().get(0).getMsg());
                            }
                            return;
                        }
                        else {
                            Intent intent = new Intent(LoginFragment.this.getContext(), MainActivity.class);
                            User user = response.body();
                            String jsonUser = new Gson().toJson(user);
                            Bundle bundle = new Bundle();
                            bundle.putString("currentUserLogin", jsonUser);
                            intent.putExtra("UserBundle", bundle);
                            loginFragmentBinding.emailEt.setText("");
                            loginFragmentBinding.passwordEt.setText("");
                            JobInfoActivity.isAdminUse = Objects.equals(user.getPrivilege_id(), "1");
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable throwable) {
                        Toast.makeText(LoginFragment.this.getContext(), "Some thing wrong check your internet connect and try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

        return loginFragmentBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginFragmentBinding = null;
    }
}