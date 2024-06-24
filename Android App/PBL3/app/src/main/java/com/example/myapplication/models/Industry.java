package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class Industry {
    @SerializedName("INDUSTRY_ID")
    int industryId;
    @SerializedName("INDUSTRY_NAME")
    String industryName;

    public Industry(int industryId, String industryName) {
        this.industryId = industryId;
        this.industryName = industryName;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
