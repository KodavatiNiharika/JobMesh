package com.jobs.jobportal.DTO;

import java.util.List;

public class MailRequest {

    private List<String> selectedUsers;
    private String jobTitle;
    private String description;
    private String location;
    private String companyName;
    private String jobLink;

    public MailRequest() {}

    public MailRequest(List<String> selectedUsers, String jobTitle,
                       String description, String location,
                       String companyName, String jobLink) {
        this.selectedUsers = selectedUsers;
        this.jobTitle = jobTitle;
        this.description = description;
        this.location = location;
        this.companyName = companyName;
        this.jobLink = jobLink;
    }

    public List<String> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<String> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }
}