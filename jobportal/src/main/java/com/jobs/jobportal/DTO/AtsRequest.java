package com.jobs.jobportal.DTO;

import java.util.List;

public class AtsRequest {
    private Long jobId;

    // ATS expects 'description'
    private String description;

    private List<ResumeData> resumes;

    // getters and setters
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ResumeData> getResumes() { return resumes; }
    public void setResumes(List<ResumeData> resumes) { this.resumes = resumes; }
}