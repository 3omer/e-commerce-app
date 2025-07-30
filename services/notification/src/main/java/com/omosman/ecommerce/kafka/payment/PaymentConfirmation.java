package com.omosman.ecommerce.kafka.payment;

import com.omosman.ecommerce.kafka.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail

) {
}
