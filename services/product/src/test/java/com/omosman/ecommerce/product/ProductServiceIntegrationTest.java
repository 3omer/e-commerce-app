package com.omosman.ecommerce.product;

import com.omosman.ecommerce.category.Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.datasource.url=jdbc:h2:mem:testdb", // optional
        "spring.datasource.driver-class-name=org.h2.Driver", // optional
        "spring.datasource.username=sa",
        "spring.datasource.password="
})
@Transactional // Rolls back DB after each test
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceIntegrationTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @BeforeAll
      void setUp(){
        var electronics = new Category();
        electronics.setName("electronics");
        var other = new Category();
        other.setName("other");

        var products = List.of(
                new Product(null, "iphone 11", "desc", 10, BigDecimal.valueOf(50), electronics),
                new Product(null, "used iphone 12", "desc", 10, BigDecimal.valueOf(90), electronics),
                new Product(null, "iphone 13", "desc", 10, BigDecimal.valueOf(91), electronics),
                new Product(null, "galaxy", "desc", 10, BigDecimal.valueOf(10), electronics),
                new Product(null, "iphone", "desc", 10, BigDecimal.valueOf(10), other)

        );

        repository.saveAll(products);

    }
    @Test
    public void filterProducts_shouldReturnCorrectData(){

        var name = "iphone";
        var categoryId = 1;
        var minPrice = BigDecimal.valueOf(10);
        var maxPrice = BigDecimal.valueOf(90);
        var pageable = PageRequest.of(0, 10, Sort.by("price").ascending());

        var result = service.filterProducts(name, categoryId, minPrice, maxPrice, pageable);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).name(), "iphone 11");

    }
}