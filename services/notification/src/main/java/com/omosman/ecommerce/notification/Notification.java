package com.omosman.ecommerce.notification;

import com.omosman.ecommerce.kafka.payment.PaymentConfirmation;
import com.omosman.ecommerce.kafka.order.OrderConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime date;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;


}
