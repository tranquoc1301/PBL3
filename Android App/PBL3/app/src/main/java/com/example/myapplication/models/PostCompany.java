package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class PostCompany {
    //companyName, staffSize, companyDescription
    @SerializedName("companyName")
    private String name;
    @SerializedName("companyLocation")
    private String location;
    @SerializedName("staffSize")
    private String staffSize;
    @SerializedName("companyDescription")
    private String description;

    public PostCompany(String name, String location, String staffSize, String description) {
        this.name = name;
        this.location = location;
        this.staffSize = staffSize;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStaffSize() {
        return staffSize;
    }

    public void setStaffSize(String staffSize) {
        this.staffSize = staffSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
