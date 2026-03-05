package com.jobs.jobportal.service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String jobTitle, String description, String location, String companyName, String jobLink) {

        try {

            String htmlContent =
                    "<div style='font-family:Arial;padding:20px;background:#0f172a;color:white'>" +

                    "<h2 style='color:#38bdf8'>New Job Opportunity</h2>" +

                    "<p><b>Company:</b> " + companyName + "</p>" +
                    "<p><b>Job Title:</b> " + jobTitle + "</p>" +
                    "<p><b>Location:</b> " + location + "</p>" +

                    "<p><b>Description:</b></p>" +
                    "<p>" + description + "</p>" +

                    "<hr style='border:0;border-top:1px solid #333;margin:20px 0;'>" +

                    "<a href='" + jobLink + "' " +
                    "style='display:inline-block;padding:12px 20px;background:#22c55e;color:white;text-decoration:none;border-radius:6px;font-weight:bold'>" +
                    "Apply Now</a>" +

                    "<p style='margin-top:30px;font-size:12px;color:#94a3b8'>" +
                    "You received this because your profile matches this job on JobMesh." +
                    "</p>" +

                    "</div>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Personalised Job Update");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}