package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class UserJob {
    @SerializedName("ID")
    private int id;
    @SerializedName("USER_ID")
    private int userId;
    @SerializedName("JOB_ID")
    private int jobId;

    public UserJob(int id, int userId, int jobId) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
