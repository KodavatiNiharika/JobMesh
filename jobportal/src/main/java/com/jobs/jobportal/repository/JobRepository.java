package com.jobs.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobs.jobportal.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}
