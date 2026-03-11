package com.airtribe.ShareMyRecipe.service.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    public EmailServiceImpl(@Autowired JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String[] recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient[0]);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("info@demomailtrap.co");
        mailSender.send(message);
    }
}
