package org.example.techblog.services;

public interface EmailService {
    void sendConfirmationEmail(String email, String token);

    void sendConfirmationEmail(String email);

    void sendContactFormEmail(String toEmail, String fullName, String email, String phone, String subject, String message);
}
