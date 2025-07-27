package com.omosman.ecommerce.order;

import com.omosman.ecommerce.customer.CustomerClient;
import com.omosman.ecommerce.exception.BusinessException;
import com.omosman.ecommerce.kafka.OrderConfirmation;
import com.omosman.ecommerce.kafka.OrderProducer;
import com.omosman.ecommerce.payment.PaymentClient;
import com.omosman.ecommerce.payment.PaymentRequest;
import com.omosman.ecommerce.product.ProductClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private final PaymentClient paymentClient;
    private final OrderRepository repository;

    private final OrderProducer orderProducer;

    private final OrderMapper mapper;


    public Order createOrder(CreateOrderRequest request) {
        // check the customer
        var customer = customerClient.getCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: Customer not found with the provided ID"));

        // purchase the product
        var purchaseResponses = this.productClient.purchaseProducts(request.products());

        // persist order and order-lines
        var order = this.repository.save(mapper.toOrder(request));

        // process payment
        var paymentId = this.paymentClient.requestOrderPayment(new PaymentRequest(
            null,
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        ));

        // send the order confirmation
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseResponses
                )
        );

        return order;
    }

    public List<CreateOrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .toList();
    }

    public CreateOrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
