package com.jobs.jobportal.Scheduler;
import com.jobs.jobportal.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobCleanupScheduler {

    private final JobRepository jobRepository;

    public JobCleanupScheduler(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Runs every 1 hour
    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredJobs() {
        jobRepository.deleteByExpiredAtBefore(LocalDateTime.now());
        System.out.println("Expired jobs cleaned at " + LocalDateTime.now());
    }
}
