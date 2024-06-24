package com.example.myapplication.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PostUser {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("retype_password")
    private String retype_password;
    @SerializedName("dateOfBirth")
    private String dob;
    @SerializedName("gender")
    private String gender;

    @SerializedName("avatar")
    private String avatar;

    public PostUser(@Nullable String name,@Nullable String email,@Nullable String password,@Nullable String retype_password,@Nullable String dob,@Nullable String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.retype_password = retype_password;
        this.dob = dob;
        this.gender = gender;
    }
    public  PostUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public PostUser(String name, String email, String dob, String gender, String avatar) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetype_password() {
        return retype_password;
    }

    public void setRetype_password(String retype_password) {
        this.retype_password = retype_password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
