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
        message.setFrom("bonnie.kerluke@ethereal.email");
        message.setTo("bonnie.kerluke@ethereal.email");
        message.setSubject("Confirm Email");
        message.setText("salam");
        //http://localhost:9090/auth/confirm?email=gulnar@itbrains.edu.az&token=asjdfnsjkf
        //template literals
        String res = "http://localhost:9595/auth/confirm?email="+email+"&token="+token;
        message.setText(res);
        mailSender.send(message);
    }
}
