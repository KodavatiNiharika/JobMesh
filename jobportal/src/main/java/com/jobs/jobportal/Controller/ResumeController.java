package com.jobs.jobportal.controller;

import com.jobs.jobportal.model.Resume;
import com.jobs.jobportal.service.ResumeService;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    
    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Resume uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {
                System.out.println("Received file: " + file.getOriginalFilename());
    System.out.println("File size: " + file.getSize());
        return resumeService.saveResume(file, userId);
    }
}
