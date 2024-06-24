package com.example.myapplication.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class User {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPrivilege_id() {
        return privilege_id;
    }

    public void setPrivilege_id(String privilege_id) {
        this.privilege_id = privilege_id;
    }

    public String getAvatar_scr() {
        return avatar_scr;
    }

    public void setAvatar_scr(String avatar_scr) {
        this.avatar_scr = avatar_scr;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public User(int id, String name, String email, String password, String gender, String date_of_birth, String privilege_id, @Nullable String avatar_scr, @Nullable String update_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.privilege_id = privilege_id;
        this.avatar_scr = avatar_scr;
        this.update_at = update_at;
    }

    @SerializedName("ID")
    private int id;
    @SerializedName("NAME")
    private String name;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("PASSWORD")
    private String password;
    @SerializedName("GENDER")
    private String gender;
    @SerializedName("DATE_OF_BIRTH")
    private String date_of_birth;
    @SerializedName("PRIVILEGE_ID")
    private String privilege_id;
    @SerializedName("AVATAR")
    private String avatar_scr;
    @SerializedName("UPDATE_AT")
    private String update_at;



}
