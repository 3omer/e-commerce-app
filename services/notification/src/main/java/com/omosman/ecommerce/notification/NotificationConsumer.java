package com.omosman.ecommerce.notification;

import com.omosman.ecommerce.kafka.order.OrderConfirmation;
import com.omosman.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationConsumer {

    private final NotificationRepository repo;
    private final EmailService emailService;

    @KafkaListener(topics = "order-topic")
    public void orderConfirmation(OrderConfirmation orderConfirmation){
        log.info(String.format("An order confirmation received: < %s >", orderConfirmation));
        repo.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
        emailService.sendOrderNotification(
                orderConfirmation.customer().email(),
                orderConfirmation.customer().fullName(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "payment-topic")
    public void paymentConfirmation(PaymentConfirmation paymentConfirmation){
        log.info(String.format("A payment confirmation received: < %s >", paymentConfirmation));
        repo.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        final var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();

        emailService.sendPaymentNotification(
               paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }
}
