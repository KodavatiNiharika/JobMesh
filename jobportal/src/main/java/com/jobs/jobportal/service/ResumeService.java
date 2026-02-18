package com.jobs.jobportal.service;

import java.util.Optional;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jobs.jobportal.model.Resume;
import com.jobs.jobportal.model.User;
import com.jobs.jobportal.repository.ResumeRepo;
import com.jobs.jobportal.repository.UserRepo;

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

    // -----------------------
    // Python Text Extraction
    // -----------------------
    public String runPythonTextExtraction(File file, String fileType) throws IOException {
        String pythonExecutable = "C:\\MyProjects\\JobPortal\\ats-service\\venv\\Scripts\\python.exe";

        String scriptPath = "C:\\MyProjects\\JobPortal\\ats-service\\TextExtraction.py"; // set correct path

        ProcessBuilder pb = new ProcessBuilder(
            pythonExecutable,
            scriptPath,
            file.getAbsolutePath(),  // pass file path as argument
            fileType                 // if you want to indicate pdf/docx
        );

        pb.redirectErrorStream(true);

        Process process = pb.start();

        // Capture output
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        // Wait for process completion
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python extraction failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
            throw new RuntimeException("Python extraction was interrupted", e);
        }

        return output.toString().trim();
    }

    // -----------------------
    // Save Resume
    // -----------------------
    public Resume saveResume(MultipartFile file, Long userId, String extractedText) throws IOException {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Resume existing = resumeRepository.findByUserId(userId);
    if (existing != null) {
        resumeRepository.delete(existing);
    }

    // ✅ READ FILE ONCE
    byte[] fileBytes = file.getBytes();

    if (extractedText == null || extractedText.isBlank()) {

        File tempFile = File.createTempFile("resume-", ".tmp");

        try {
            // write bytes manually instead of transferTo
            java.nio.file.Files.write(tempFile.toPath(), fileBytes);

            String fileType = file.getOriginalFilename().toLowerCase().endsWith(".pdf") ? "pdf" : "docx";
            extractedText = runPythonTextExtraction(tempFile, fileType);

        } finally {
            tempFile.delete();
        }
    }

    Resume resume = new Resume();
    resume.setUser(user);
    resume.setFileName(file.getOriginalFilename());
    resume.setFileData(fileBytes);   // ✅ SAFE now
    resume.setFullText(extractedText);

    return resumeRepository.save(resume);
}


    // -----------------------
    // Get Resume by ID
    // -----------------------
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    // -----------------------
    // Delete Resume
    // -----------------------
    public void deteleResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        resumeRepository.delete(resume);
    }

    // -----------------------
    // Get Resume by User
    // -----------------------
    public Resume getResumeByUserId(Long userId) {
        return resumeRepository.findByUserId(userId);
    }
}
