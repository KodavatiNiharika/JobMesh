package com.jobs.jobportal.controller;
import com.jobs.jobportal.repository.*;
import com.jobs.jobportal.model.Resume;
import com.jobs.jobportal.service.ResumeService;

import java.io.IOException;

import org.apache.catalina.connector.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return resumeService.saveResume(file, userId);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewResume(@PathVariable Long id) {
        Resume resume = resumeService.getResumeById(id);
        String contentType = 
                resume.getFileName().endsWith(".pdf") 
                ? "application/pdf" 
                : "application/octet-stream";
        return ResponseEntity.ok()
            .header("Content-Disposition", "inline; filename=\"" + resume.getFileName() + "\"") //content disposition prevents download
            .contentType(MediaType.parseMediaType(contentType))
            .body(resume.getFileData());
    }

    @GetMapping("/user/{userId}")
public ResponseEntity<Resume> getResumeByUser(@PathVariable Long userId) {
    Resume resume = resumeService.getResumeByUserId(userId);


    if (resume == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(resume);
}



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deteleResumeById(@PathVariable Long id) {
        resumeService.deteleResumeById(id);
        return ResponseEntity.ok("Resume deleted successfully!");
    }

}
