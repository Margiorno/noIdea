package com.pm.noidea.notificationservice;

import com.pm.noidea.notificationservice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceApplicationTests {

    @Autowired
    private EmailService emailService;

//    @Test
//    void emailSendToMe() {
//        emailService.sendVerificationCode("m.zawiski03@gmail.com","test");
//    }

}
