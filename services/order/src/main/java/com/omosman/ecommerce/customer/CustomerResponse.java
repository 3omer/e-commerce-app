package com.omosman.ecommerce.customer;

public record CustomerResponse(
        String id,
        String email,
        String firstname,
        String lastname
) {
}
