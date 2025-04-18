package com.omosman.ecommerce.product;

import com.omosman.ecommerce.category.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .availableQuantity(productRequest.availableQuantity())
                .category(
                        Category.builder()
                                .id(productRequest.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse fromProduct(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId());
    }
}
