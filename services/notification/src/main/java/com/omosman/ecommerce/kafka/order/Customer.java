package com.omosman.ecommerce.kafka.order;

public record Customer(
        String id,
        String email,
        String firstname,
        String lastname
) {

    public String fullName() {
        return firstname + " " + lastname;
    }

}
