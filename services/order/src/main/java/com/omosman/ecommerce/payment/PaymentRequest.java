package com.omosman.ecommerce.payment;

import com.omosman.ecommerce.customer.CustomerResponse;
import com.omosman.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}

