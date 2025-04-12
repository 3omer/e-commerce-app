package com.omosman.ecommerce.product;

import com.omosman.ecommerce.category.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Category category
) {
}
