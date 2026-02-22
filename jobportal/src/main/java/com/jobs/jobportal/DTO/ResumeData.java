package com.jobs.jobportal.DTO;

public class ResumeData {
    private String userName;

    // ATS expects this field to be called fullText
    private String fullText;

    // getters and setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getFullText() { return fullText; }
    public void setFullText(String fullText) { this.fullText = fullText; }
}