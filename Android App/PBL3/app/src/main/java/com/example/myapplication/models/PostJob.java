package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class PostJob {
    @SerializedName("JOB_NAME")
    private String jobName;
    @SerializedName("COMPANY_ID")
    private int companyId;
    @SerializedName("INDUSTRY")
    private String industry;
    @SerializedName("LOCATION")
    private String location;

    @SerializedName("COMPANY_NAME")
    private String companyName;

    @SerializedName("ENROLLMENT_LOCATION")
    private String enrollmentLocation;
    @SerializedName("ROLE")
    private String role;
    @SerializedName("SALARY")
    private String salary;
    @SerializedName("GENDER_REQUIREMENT")
    private String genderRequirement;
    @SerializedName("NUMBER_OF_RECRUITMENT")
    private String numberOfRecruitment;
    @SerializedName("AGE_REQUIREMENT")
    private String ageRequirement;
    @SerializedName("EXPERIENCE_REQUIREMENT")
    private String experienceRequirement;
    @SerializedName("BENEFITS")
    private String benefits;
    @SerializedName("JOB_DESCRIPTION")
    private String jobDescription;
    @SerializedName("JOB_REQUIREMENT")
    private String jobRequirement;
    @SerializedName("SOURCE_PICTURE")
    private String sourcePicture;

    public PostJob(String jobName, String companyName, String industry, String location, String enrollmentLocation, String role, String salary, String genderRequirement, String numberOfRecruitment, String ageRequirement, String experienceRequirement, String benefits, String jobDescription, String jobRequirement, String sourcePicture) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.industry = industry;
        this.location = location;
        this.enrollmentLocation = enrollmentLocation;
        this.role = role;
        this.salary = salary;
        this.genderRequirement = genderRequirement;
        this.numberOfRecruitment = numberOfRecruitment;
        this.ageRequirement = ageRequirement;

        this.experienceRequirement = experienceRequirement;
        this.benefits = benefits;
        this.jobDescription = jobDescription;
        this.jobRequirement = jobRequirement;
        this.sourcePicture = sourcePicture;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getEnrollmentLocation() {
        return enrollmentLocation;
    }

    public void setEnrollmentLocation(String enrollmentLocation) {
        this.enrollmentLocation = enrollmentLocation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getGenderRequirement() {
        return genderRequirement;
    }

    public void setGenderRequirement(String genderRequirement) {
        this.genderRequirement = genderRequirement;
    }

    public String getNumberOfRecruitment() {
        return numberOfRecruitment;
    }

    public void setNumberOfRecruitment(String numberOfRecruitment) {
        this.numberOfRecruitment = numberOfRecruitment;
    }

    public String getAgeRequirement() {
        return ageRequirement;
    }

    public void setAgeRequirement(String ageRequirement) {
        this.ageRequirement = ageRequirement;
    }
    public String getExperienceRequirement() {
        return experienceRequirement;
    }

    public void setExperienceRequirement(String experienceRequirement) {
        this.experienceRequirement = experienceRequirement;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    public String getSourcePicture() {
        return sourcePicture;
    }

    public void setSourcePicture(String sourcePicture) {
        this.sourcePicture = sourcePicture;
    }
}
