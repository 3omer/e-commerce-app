package com.omosman.ecommerce.product;

import com.omosman.ecommerce.category.Category;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Category category
) {
}
