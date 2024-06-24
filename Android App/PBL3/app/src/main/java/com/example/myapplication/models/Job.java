package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Job {
    @SerializedName("JOB_ID")
    private int jobId;
    @SerializedName("JOB_NAME")
    private String jobName;
    @SerializedName("COMPANY_ID")
    private int companyId;
    @SerializedName("INDUSTRY")
    private String industry;
    @SerializedName("LOCATION")
    private String location;
    @SerializedName("POSTED_DATE")
    private String postedDate;
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
    @SerializedName("PROBATION_TIME")
    private String probationTime;
    @SerializedName("WORKWAY")
    private String workWay;
    @SerializedName("EXPERIENCE_REQUIREMENT")
    private String experienceRequirement;
    @SerializedName("DEGREE_REQUIREMENT")
    private String degreeRequirement;
    @SerializedName("BENEFITS")
    private String benefits;
    @SerializedName("JOB_DESCRIPTION")
    private String jobDescription;
    @SerializedName("JOB_REQUIREMENT")
    private String jobRequirement;
    @SerializedName("DEADLINE")
    private String deadline;
    @SerializedName("SOURCE_PICTURE")
    private String sourcePicture;

    public Job(int jobId, String jobName, int companyId, String industry, String location, String postedDate, String enrollmentLocation, String role, String salary, String genderRequirement, String numberOfRecruitment, String ageRequirement, String probationTime, String workWay, String experienceRequirement, String degreeRequirement, String benefits, String jobDescription, String jobRequirement, String deadline, String sourcePicture) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.companyId = companyId;
        this.industry = industry;
        this.location = location;
        this.postedDate = postedDate;
        this.enrollmentLocation = enrollmentLocation;
        this.role = role;
        this.salary = salary;
        this.genderRequirement = genderRequirement;
        this.numberOfRecruitment = numberOfRecruitment;
        this.ageRequirement = ageRequirement;
        this.probationTime = probationTime;
        this.workWay = workWay;
        this.experienceRequirement = experienceRequirement;
        this.degreeRequirement = degreeRequirement;
        this.benefits = benefits;
        this.jobDescription = jobDescription;
        this.jobRequirement = jobRequirement;
        this.deadline = deadline;
        this.sourcePicture = sourcePicture;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
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

    public String getProbationTime() {
        return probationTime;
    }

    public void setProbationTime(String probationTime) {
        this.probationTime = probationTime;
    }

    public String getWorkWay() {
        return workWay;
    }

    public void setWorkWay(String workWay) {
        this.workWay = workWay;
    }

    public String getExperienceRequirement() {
        return experienceRequirement;
    }

    public void setExperienceRequirement(String experienceRequirement) {
        this.experienceRequirement = experienceRequirement;
    }

    public String getDegreeRequirement() {
        return degreeRequirement;
    }

    public void setDegreeRequirement(String degreeRequirement) {
        this.degreeRequirement = degreeRequirement;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSourcePicture() {
        return sourcePicture;
    }

    public void setSourcePicture(String sourcePicture) {
        this.sourcePicture = sourcePicture;
    }

    public int findCompanyById(List<Company> companyList, int id)
    {
        for (int i = 0; i < companyList.size(); i++) {
            if (id == companyList.get(i).getId()) {
                companyList.get(i).setCompanyLogo(this.sourcePicture);
                return i;
            }
        }
        return -1;
    }

}
