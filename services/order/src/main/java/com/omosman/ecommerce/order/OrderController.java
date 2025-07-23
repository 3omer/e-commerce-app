package com.omosman.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private OrderService service;

    private OrderMapper mapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(
            @RequestBody @Valid CreateOrderRequest request
    ) {
        var order = service.createOrder(request);

        return ResponseEntity.ok(mapper.fromOrder(order));

    }

    @GetMapping
    public ResponseEntity<List<CreateOrderResponse>> findAll() {
        return ResponseEntity.ok(this.service.findAllOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<CreateOrderResponse> findById(
            @PathVariable("order-id") Integer orderId
    ) {
        return ResponseEntity.ok(this.service.findById(orderId));
    }
}
