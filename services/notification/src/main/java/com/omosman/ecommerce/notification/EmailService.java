package com.omosman.ecommerce.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {


    private MailSender mailSender;

    @Async
    public void sendOrderNotification(
            String destinationEmail,
            String CustomerName,
            BigDecimal amount,
            String orderRef
    ) {
        log.info("Sending order confirmation email to {}, ref {}", destinationEmail, orderRef);
    }

    @Async
    public void sendPaymentNotification(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderRef
    ) {
        log.info("Sending payment confirmation email to {}, ref {}", destinationEmail, orderRef);

    }
}
