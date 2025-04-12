package com.omosman.ecommerce;

import com.omosman.ecommerce.product.ProductController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(properties = {
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false",
		"spring.flyway.enabled=false",
		"spring.jpa.hibernate.ddl-auto=none",
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
		"spring.datasource.url=jdbc:h2:mem:testdb", // optional
		"spring.datasource.driver-class-name=org.h2.Driver", // optional
		"spring.datasource.username=sa",
		"spring.datasource.password="
})
class ProductApplicationTests {

	@Autowired
	private ProductController productController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(productController);
	}

}
