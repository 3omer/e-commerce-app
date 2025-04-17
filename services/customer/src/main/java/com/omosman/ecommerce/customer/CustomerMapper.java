package com.omosman.ecommerce.customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        if (request == null)
            return null;

        return Customer.builder()
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getAddress()
        );
    }
}
