package org.example.techblog.services.impl;

import org.example.techblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendConfirmationEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gulnarnaghiyeva9@gmail.com");
        message.setTo("gulnarnaghiyeva9@gmail.com");
        message.setSubject("Confirm Email");
        message.setText("salam");

        String res = "http://localhost:9595/auth/confirm?email="+email+"&token="+token;
        message.setText(res);
        mailSender.send(message);
    }

    @Override
    public void sendConfirmationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gulnarnaghiyeva9@gmail.com");
        message.setTo("gulnarnaghiyeva9@gmail.com");
        message.setSubject("Contacting Email");
        message.setText("salam");
        String res = email;
        message.setText(res);
        mailSender.send(message);
    }

    @Override
    public void sendContactFormEmail(String toEmail, String fullName, String email, String phone, String subject, String message) {
        SimpleMailMessage adminMailMessage = new SimpleMailMessage();
        adminMailMessage.setFrom("gulnarnaghiyeva9@gmail.com");
        adminMailMessage.setTo("gulnarnaghiyeva9@gmail.com");
        adminMailMessage.setSubject("Contacting Email");
        adminMailMessage.setText("Full Name: " + fullName +
                "\nEmail: " + email +
                "\nPhone: " + phone +
                "\nMessage: " + message);
        mailSender.send(adminMailMessage);


        SimpleMailMessage userMailMessage = new SimpleMailMessage();
        userMailMessage.setFrom("gulnarnaghiyeva9@gmail.com");
        userMailMessage.setTo(email);
        userMailMessage.setSubject("Thank You for Contacting Us");
        userMailMessage.setText("Thank you for contacting us, " + fullName + ". We have received your message and will get back to you shortly.");
        mailSender.send(userMailMessage);
    }



}
