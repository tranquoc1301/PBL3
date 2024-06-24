package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class Company {
    @SerializedName("ID")
    private int id;
    @SerializedName("COMPANY_NAME")
    private String companyName;
    @SerializedName("LOCATION")
    private String location;
    @SerializedName("STAFF_SIZE")
    private String staffSize;
    @SerializedName("COMPANY_DESCRIPTION")
    private String companyDescription;

    private String companyLogo;

    public Company() {

    }

    public Company(int id, String companyName, String location, String staffSize, String companyDescription) {
        this.id = id;
        this.companyName = companyName;
        this.location = location;
        this.staffSize = staffSize;
        this.companyDescription = companyDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}