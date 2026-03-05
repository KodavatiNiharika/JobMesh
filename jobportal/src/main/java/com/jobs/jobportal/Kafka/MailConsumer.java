package com.jobs.jobportal.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobs.jobportal.DTO.MailRequest;
import com.jobs.jobportal.service.MailService;

@Component
public class MailConsumer {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    public MailConsumer(MailService mailService, ObjectMapper objectMapper) {
        this.mailService = mailService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "mail-topic", groupId = "mail-group")
    public void processMailMessage(String message) {
        try {

            MailRequest request = objectMapper.readValue(message, MailRequest.class);

            String emailBody =
                    "<div style='background:#121212;padding:30px;font-family:Inter,Arial,sans-serif;color:#E0E0E0;'>" +

                    "<div style='max-width:520px;margin:auto;background:#1E1E1E;padding:30px;border-radius:12px;'>" +

                    "<h2 style='color:#F5F5F5;margin-bottom:8px;'>"
                    + request.getJobTitle() +
                    "</h2>" +

                    "<p style='color:#9CA3AF;margin-bottom:12px;'>"
                    + "<b>Company:</b> " + request.getCompanyName() +
                    "</p>" +

                    "<p style='color:#B0B0B0;margin-bottom:12px;'>"
                    + "<b>Location:</b> " + request.getLocation() +
                    "</p>" +

                    "<hr style='border:0;border-top:1px solid #333;margin:20px 0;'>" +

                    "<p style='color:#CFCFCF;margin-bottom:25px;line-height:1.6;'>"
                    + request.getDescription() +
                    "</p>" +

                    "<a href='" + request.getJobLink() + "' " +
                    "style='background:#22c55e;color:white;padding:12px 24px;text-decoration:none;border-radius:6px;font-weight:600;display:inline-block;'>"
                    + "Apply Now</a>" +

                    "<p style='margin-top:30px;font-size:12px;color:#8A8A8A;'>"
                    + "You received this job recommendation because your profile matched this opportunity."
                    + "</p>" +

                    "</div></div>";

            for (String email : request.getSelectedUsers()) {
                mailService.sendEmail(
                    email,
                    request.getJobTitle(),
                    request.getDescription(),
                    request.getLocation(),
                    request.getCompanyName(),
                    request.getJobLink()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}