package com.omosman.customer.customer;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String id,
        String email,
        String firstname,
        String lastname,
        Address address
) {
}
