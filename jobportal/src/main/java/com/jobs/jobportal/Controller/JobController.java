package com.jobs.jobportal.controller;

import com.jobs.jobportal.model.Job;
import com.jobs.jobportal.repository.JobRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostMapping("/create")
    public Job createJob(@RequestBody Job job) {
        job.setCreatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    @GetMapping
    public java.util.List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
