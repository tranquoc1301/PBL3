package com.example.myapplication.controller;


import com.example.myapplication.models.CV;
import com.example.myapplication.models.Company;
import com.example.myapplication.models.Industry;
import com.example.myapplication.models.Job;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.PostCompany;
import com.example.myapplication.models.PostJob;
import com.example.myapplication.models.PostUser;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UserJob;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService{

    String url = "http://192.168.1.7:3000";

    Gson gson = new Gson().newBuilder().setLenient().create();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build();

    ApiService apiService = retrofit.create(ApiService.class);


    @POST("/register")
    Call<Message> postRegister(@Body PostUser user);

    @POST("/login")
    Call<User> postLogin(@Body PostUser user);

    @GET("/job/job-list")
    Call<List<Job>> getJobList();

    @GET("/industry/industry-list")
    Call<List<Industry>> getIndustryList();

    @GET("/company/company-list")
    Call<List<Company>> getCompanyList();

    @GET("/job/job-list-by-company")
    Call<List<Job>> getJobListByCompany(@Query("companyId") int companyId);

    @GET("/company/id")
    Call<Company> getCompanyById(@Query("companyId") int companyId);

    @GET("/job/job-list-by-name")
    Call<List<Job>> getJobListByName(@Query("jobName") String name);


    @PUT("/user/update")
    Call<Message> updateUser(@Query("id")int id, @Body PostUser user);

    @GET("/user/user-job/my-apply-job")
    Call<List<UserJob>> getUserJob(@Query("userID")int userId, @Query("jobID")int jobId);

    @POST("/user/user-job/create")
    Call<Message> createUserJob(@Query("userID") int userId, @Query("jobID") int jobId);

    @DELETE("/user/user-job/delete")
    Call<Message> deleteUserJob(@Query("id") int id);

    @GET("/user/user-job/my-apply-job-list")
    Call<List<UserJob>> getApplyList(@Query("userID") int userID);

    @GET("/job/job-list-by-id")
    Call<Job> getJobById(@Query("id") int id);

    @DELETE("/company/delete")
    Call<Message> deleteCompany(@Query("id") int id);

    @PUT("/company/update")
    Call<Message> updateCompany(@Query("id") int id, @Body PostCompany company);

    @POST("/company/create")
    Call<Message> createCompany(@Body PostCompany company);

    @PUT("/job/update")
    Call<Message> updateJob(@Query("id") int id, @Body Job job);

    @POST("/job/create")
    Call<Message> createJob(@Body PostJob job);

    @DELETE("/job/delete")
    Call<Message> deleteJob(@Query("id") int id);

    @GET("/user/user-list")
    Call<List<User>> getListUser();

    @DELETE("/user/delete")
    Call<Message> deleteUser(@Query("id") int id);

    @GET("/company/search")
    Call<List<Company>> searchCompany(@Query("companyName") String name);

    @GET("/user/search")
    Call<List<User>> searchUser(@Query("userName") String name);

    @PUT("/user/to-admin")
    Call<Message> toAdmin(@Query("id") int id);
    @Multipart
    @POST("/cv/upload")
    Call<Message> uploadCV(@Part MultipartBody.Part file, @Query("id") int userId);

    @GET("/user/my-cv/getCV")
    Call<List<CV>> getCvByUserId(@Query("id") int id);

    @GET("/cv/my-cv")
    Call<String> getFile(@Query("id") int id);

    @DELETE("/user/my-cv/delete")
    Call<Message> deleteCV(@Query("id") int id);

    @GET("/job/apply-list")
    Call<List<User>> getApplyOfJobList(@Query("id") int id);

    @GET("/user")
    Call<User> getUserById(@Query("id") int id);
}
