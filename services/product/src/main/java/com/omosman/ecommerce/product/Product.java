package com.omosman.ecommerce.product;

import com.omosman.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private  String description;
    private  double availableQuantity;
    private BigDecimal price;

    @ManyToOne
    private Category category;
}
