package com.example.myapplication.view;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.HeaderNavBinding;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final public static int HOME_PAGE = 1;
    final public static int ALL_JOB = 2;
    final public static int COMPANY = 3;
    final public static int USER_PAGE = 4;
    final public static int ADMIN_COMPANY = 5;
    final public static int MY_APPLY = 6;
    final public static int ADMIN_JOB = 7;

    final public static int ADMIN_USER = 8;

    final public static int ABOUT_US = 9;
    public int currentPage = HOME_PAGE;

    public int currentUserIdLogin;

    public User userLogin;

    HeaderNavBinding headerNavBinding;
    private ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCallBackButton();
        setUserInfoToNavHead();


        User user = new Gson().fromJson(getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin"), User.class);
        this.currentUserIdLogin = user.getId();

        this.userLogin = new Gson().fromJson(getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin"), User.class);



        mainBinding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBinding.drawLayout.openDrawer(mainBinding.navView);
            }
        });

//        mainBinding.searchBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (currentPage != ALL_JOB) {
//                    replaceFragment(new AllJobFragment());
//                    currentPage = ALL_JOB;
//                }
//            }
//        });

//        mainBinding.searchBar.

        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mainBinding.navView.isActivated()) {
                    MainActivity.super.getOnBackPressedDispatcher().onBackPressed();
                }
                else {
                    moveTaskToBack(true);
                    finish();
                }
            }
        });
        mainBinding.searchBar.setEnabled(false);
        mainBinding.searchBar.setIconified(false);
        mainBinding.searchBar.clearFocus();
        mainBinding.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBinding.searchBar.setIconified(false);
            }
        });

        mainBinding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                switch (currentPage) {
                    case ALL_JOB:
                        ApiService.apiService.getJobListByName(query).enqueue(new Callback<List<Job>>() {
                            @Override
                            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                                if (currentPage != ALL_JOB) {
                                    replaceFragment(new AllJobFragment());
                                    currentPage = ALL_JOB;
                                }
                                if (response.isSuccessful()) {
                                    AllJobFragment allJobFragment = (AllJobFragment) mainBinding.fragmentContainerView5.getFragment();
                                    allJobFragment.changeJobList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Job>> call, Throwable throwable) {
                                if (currentPage != ALL_JOB) {
                                    replaceFragment(new AllJobFragment());
                                    currentPage = ALL_JOB;
                                }
                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case ADMIN_COMPANY:
                        ApiService.apiService.searchCompany(query).enqueue(new Callback<List<Company>>() {
                            @Override
                            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                                if (response.isSuccessful()) {
                                    AdminCompanyFragment adminCompanyFragment = (AdminCompanyFragment) mainBinding.fragmentContainerView5.getFragment();
                                    adminCompanyFragment.changeCompanyList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Company>> call, Throwable throwable) {

                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case ADMIN_USER:

                        break;
                    case ADMIN_JOB:

                        break;
                    case COMPANY:
                        break;
                }

                return false;
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                if (currentPage == HOME_PAGE || currentPage == USER_PAGE || currentPage == MY_APPLY || currentPage == ABOUT_US) {
                    replaceFragment(new AllJobFragment());
                    currentPage = ALL_JOB;
                }
                switch (currentPage) {
                    case ALL_JOB:
                        ApiService.apiService.getJobListByName(newText).enqueue(new Callback<List<Job>>() {
                            @Override
                            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {

                                if (response.isSuccessful()) {
                                    AllJobFragment allJobFragment = (AllJobFragment) mainBinding.fragmentContainerView5.getFragment();
                                    allJobFragment.changeJobList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Job>> call, Throwable throwable) {
                                if (currentPage != ALL_JOB) {
                                    replaceFragment(new AllJobFragment());
                                    currentPage = ALL_JOB;
                                }
                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case ADMIN_COMPANY:
                        ApiService.apiService.searchCompany(newText).enqueue(new Callback<List<Company>>() {
                            @Override
                            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                                if (response.isSuccessful()) {
                                    AdminCompanyFragment adminCompanyFragment = (AdminCompanyFragment) mainBinding.fragmentContainerView5.getFragment();
                                    adminCompanyFragment.changeCompanyList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Company>> call, Throwable throwable) {

                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case ADMIN_USER:
                        ApiService.apiService.searchUser(newText).enqueue(new Callback<List<User>>() {
                            @Override
                            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                if (response.isSuccessful()) {
                                    AdminUserFragment adminUserFragment = (AdminUserFragment) mainBinding.fragmentContainerView5.getFragment();
                                    adminUserFragment.changeUserList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<User>> call, Throwable throwable) {
                                if (currentPage != ALL_JOB) {
                                    replaceFragment(new AllJobFragment());
                                    currentPage = ALL_JOB;
                                }
                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case ADMIN_JOB:
                        ApiService.apiService.getJobListByName(newText).enqueue(new Callback<List<Job>>() {
                            @Override
                            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                                if (response.isSuccessful()) {
                                    AdminJobFragment adminJobFragment = (AdminJobFragment) mainBinding.fragmentContainerView5.getFragment();
                                    adminJobFragment.changeJobList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Job>> call, Throwable throwable) {
                                if (currentPage != ALL_JOB) {
                                    replaceFragment(new AllJobFragment());
                                    currentPage = ALL_JOB;
                                }
                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case COMPANY:
                        ApiService.apiService.searchCompany(newText).enqueue(new Callback<List<Company>>() {
                            @Override
                            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                                if (response.isSuccessful()) {
                                    AllCompanyFragment allCompanyFragment = (AllCompanyFragment) mainBinding.fragmentContainerView5.getFragment();
                                    allCompanyFragment.changeCompanyList(response.body());
                                }
                                else {
                                    Toast.makeText(mainBinding.getRoot().getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Company>> call, Throwable throwable) {

                                Toast.makeText(mainBinding.getRoot().getContext(), "Fail on call api", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;

                }

                return false;
            }
        });


        replaceFragment(new HomeFragment());
        mainBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    if (currentPage != HOME_PAGE) {
                        replaceFragment(new HomeFragment());
                        getOnBackPressedDispatcher().onBackPressed();
                        currentPage = HOME_PAGE;
                    }
                    return true;
                }
                if (id == R.id.nav_job) {
                    if (currentPage != ALL_JOB) {
                        replaceFragment(new AllJobFragment());
                        getOnBackPressedDispatcher().onBackPressed();
                        currentPage = ALL_JOB;
                    }
                    return true;
                }
                if (id == R.id.nav_companies) {
                    if (currentPage != COMPANY) {
                        replaceFragment(new AllCompanyFragment());
                        getOnBackPressedDispatcher().onBackPressed();
                        currentPage = COMPANY;
                    }
                    return true;

                }
                if (id == R.id.nav_logout) {
                    MainActivity.this.finish();
                    return true;
                }

                if (id == R.id.adminCompany) {
                    if (currentPage != ADMIN_COMPANY) {
                        AdminCompanyFragment adminCompanyFragment = new AdminCompanyFragment();
                        adminCompanyFragment.isAdminUser(true);
                        replaceFragment(adminCompanyFragment);
                        getOnBackPressedDispatcher().onBackPressed();
                        currentPage = ADMIN_COMPANY;
                    }
                    return true;
                }
                if (id == R.id.adminJob) {
                    if (currentPage != ADMIN_JOB) {
                        AdminJobFragment adminJobFragment = new AdminJobFragment();
                        replaceFragment(adminJobFragment);
                        getOnBackPressedDispatcher().onBackPressed();
                        currentPage = ADMIN_JOB;
                    }
                    return true;
                }
                if (id == R.id.my_apply_job) {
                    if (currentPage != MY_APPLY) {

                        String jsonUser = getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
                        User user = new Gson().fromJson(jsonUser, User.class);
                        ApplyFragment applyFragment = ApplyFragment.newIntent(user.getId());
                        replaceFragment(applyFragment);
                        getOnBackPressedDispatcher().onBackPressed();
                        currentPage = MY_APPLY;
                    }
                    return true;
                }
                if (id == R.id.adminUser) {
                    if (currentPage != ADMIN_USER) {
                        replaceFragment(new AdminUserFragment());
                        currentPage = ADMIN_USER;
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                }
                if (id == R.id.nav_about_us) {
                    if (currentPage != ABOUT_US) {
                        replaceFragment(new AboutUsFragment());
                        currentPage = ABOUT_US;
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                }



                return false;
            }

        });

        mainBinding.navView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage != USER_PAGE) {
                    String jsonUser = new Gson().toJson(userLogin);
                    replaceFragment(new UserInfoFragment().newInstance(jsonUser));
                    currentPage = USER_PAGE;
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

//        mainBinding.navView.getMenu().getItem(3).setEnabled(false);
//        mainBinding.navView.getMenu().getItem(3).setVisible(false);

        if (Objects.equals(userLogin.getPrivilege_id(), "1")) {
                    mainBinding.navView.getMenu().getItem(3).setVisible(true);
        }

        setContentView(mainBinding.getRoot());
    }

    public void setCallBackButton() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mainBinding.drawLayout.isDrawerOpen(mainBinding.navView)) {
                    mainBinding.drawLayout.closeDrawer(mainBinding.navView);
                }
                else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this,callback);
    }

      public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(mainBinding.fragmentContainerView5.getId(),fragment);
        fragmentTransaction.commit();
    }

    public void setUserInfoToNavHead() {
        String jsonUser = getIntent().getExtras().getBundle("UserBundle").getString("currentUserLogin");
        User user = new Gson().fromJson(jsonUser, User.class);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        headerNavBinding = HeaderNavBinding.bind(mainBinding.navView.getHeaderView(0));
        headerNavBinding.emailTv.setText(user.getEmail());
        if (user.getAvatar_scr() != null && !user.getAvatar_scr().isEmpty()) {
            Picasso.get().load(user.getAvatar_scr()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).into(headerNavBinding.avatar);
        }
        headerNavBinding.nameTv.setText(user.getName());
    }

    public void showAdminMenu() {
        this.mainBinding.navView.getMenu().getItem(3).setVisible(true);
    }

    public ActivityMainBinding getBinding() {
        return this.mainBinding;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.currentPage == ADMIN_COMPANY) {
            AdminCompanyFragment adminCompanyFragment = new AdminCompanyFragment();
            adminCompanyFragment.isAdminUser(true);
            replaceFragment(adminCompanyFragment);
        }
        if (this.currentPage == ADMIN_JOB) {
            replaceFragment(new AdminJobFragment());
        }
    }

    public void userUpdate() {
        ApiService.apiService.getUserById(userLogin.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userLogin = response.body();
                    if (!Objects.equals(userLogin.getAvatar_scr(), "")) {
                        Picasso.get().load(userLogin.getAvatar_scr()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).into(headerNavBinding.avatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(mainBinding.getRoot().getContext(), "Fails on call API", Toast.LENGTH_LONG).show();
            }
        });
    }
}