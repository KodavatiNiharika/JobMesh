package com.jobs.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync // for background execution
public class JobportalApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobportalApplication.class, args);
    }
}
