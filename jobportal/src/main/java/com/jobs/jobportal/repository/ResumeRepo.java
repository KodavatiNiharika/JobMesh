package com.jobs.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobs.jobportal.model.Resume;
public interface ResumeRepo extends JpaRepository<Resume, Long>{  //JpaRepository< Entity class, Type of primary key >
    Resume findByUserId(Long userId);

}