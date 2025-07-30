package com.omosman.ecommerce.order;

import com.omosman.ecommerce.orderline.OrderLine;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderMapper {
    public CreateOrderResponse fromOrder(Order order) {
        return new CreateOrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }

    public Order toOrder(CreateOrderRequest request) {
        var order = Order.builder()
                .reference(request.reference())
                .customerId(request.customerId())
                .paymentMethod(request.paymentMethod())
                .totalAmount(request.amount())
                .build();

        Set<OrderLine> orderLines = request.products()
                .stream()
                .map(p -> new OrderLine(null,
                        order,
                        p.productId(),
                        p.quantity()))
                .collect(Collectors.toSet());

        order.setOrderLines(orderLines);
        return order;
    }
}
