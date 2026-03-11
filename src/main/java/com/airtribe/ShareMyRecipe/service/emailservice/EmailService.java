package com.airtribe.ShareMyRecipe.service.emailservice;

public interface EmailService {

    void sendEmail(String[] recipient, String subject, String body);
}
