package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class CV {
    @SerializedName("ID")
    int id;
    @SerializedName("USERID")
    int userId;
    @SerializedName("PATH")
    String path;
    @SerializedName("FILENAME")
    String name;

    public CV(int id, int userId, String path, String name) {
        this.id = id;
        this.userId = userId;
        this.path = path;
        this.name = name;
    }

    public CV(int userId, String path) {
        this.userId = userId;
        this.path = path;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
