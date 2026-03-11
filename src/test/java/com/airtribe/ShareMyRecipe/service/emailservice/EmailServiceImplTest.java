package com.airtribe.ShareMyRecipe.service.emailservice;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    // We have to create Mocks of all the dependencies of EmailServiceImpl
    @Mock
    private JavaMailSender mailSender;

    // @InjectMocks tells Mockito: "Create this class and inject any available mocks into it."
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void testSendEmail() {
        emailService.sendEmail(new String[] {"faisalprofessional1@gmail.com"},
                "This is the integration email", "Testing the send email");
    }
}
