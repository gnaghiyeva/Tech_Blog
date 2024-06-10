package org.example.techblog.services;

public interface EmailService {
    void sendConfirmationEmail(String email, String token);
}
