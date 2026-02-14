package com.jobs.jobportal.service;
import java.util.Optional;

import com.jobs.jobportal.model.Resume;
import com.jobs.jobportal.model.User;
import com.jobs.jobportal.repository.ResumeRepo;
import com.jobs.jobportal.repository.UserRepo;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    private final ResumeRepo resumeRepository;
    private final UserRepo userRepository;

    public ResumeService(ResumeRepo resumeRepository, UserRepo userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public Resume saveResume(MultipartFile file, Long userId) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        User user;
        if(userOpt.isPresent()) {
            user = userOpt.get();
            System.out.println("User found: " + user.getEmail());
        } else {
            System.out.println("User not found for ID: " + userId);
            throw new RuntimeException("User not found");
        }

        Resume existing = resumeRepository.findByUserId(userId);
        if (existing != null) {
            resumeRepository.delete(existing);
        }
        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFileName(file.getOriginalFilename());
        resume.setFileData(file.getBytes()); 
        System.out.println(resume.getId());
         try {
            Resume savedResume = resumeRepository.save(resume);
            System.out.println("Saved resume ID: " + savedResume.getId());
            return savedResume;
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            while (cause != null) {
                System.out.println("Cause: " + cause);
                cause = cause.getCause();
            }
            throw new RuntimeException("Failed to save resume", e); 
        }
    }

    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    public void deteleResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new RuntimeException("Resume not found"));
        resumeRepository.delete(resume);
    }
    

public Resume getResumeByUserId(Long userId) {
    
    return resumeRepository.findByUserId(userId);
}




}

