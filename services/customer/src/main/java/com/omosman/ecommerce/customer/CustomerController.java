package com.omosman.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        var customers = service.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String customerId) {
        var customer = service.getCustomerById(customerId);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> existsById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.getCustomerById(customerId) != null);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        var customer = service.createCustomer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customer);
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(
            @RequestBody @Valid UpdateCustomerRequest request
    ) {
        var customer = service.updateCustomer(request);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable String customerId
    ) {
        service.deleteCustomerById(customerId);
        return ResponseEntity.ok().build();
    }

}
