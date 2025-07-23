package com.omosman.ecommerce.order;

import com.omosman.ecommerce.customer.CustomerClient;
import com.omosman.ecommerce.customer.CustomerResponse;
import com.omosman.ecommerce.product.ProductClient;
import com.omosman.ecommerce.product.PurchaseResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private ProductClient productClient;
    @Mock
    private CustomerClient customerClient;

    private OrderRepository repository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldSaveOrderAndOrderLines() {

        when(customerClient.getCustomerById("123"))
                .thenReturn(Optional.of(
                        new CustomerResponse("123",
                        "foo@bar.com",
                                "Foo",
                                "Buzz"))
                );

        when(productClient.purchaseProducts(any()))
                .thenReturn(Set.of(
                        new PurchaseResponse(1,
                                "test a",
                                "test a",
                                BigDecimal.valueOf(10),
                                1),
                        new PurchaseResponse(2,
                                "test b",
                                "test b",
                                BigDecimal.valueOf(10),
                                1)
                        ));


        var req = new CreateOrderRequest("123",
                "123",
                PaymentMethod.CREDIT_CARD,
                BigDecimal.valueOf(10),
                Set.of(new PurchaseRequest(1, 1),
                        new PurchaseRequest(2, 1))
                );

        orderService.createOrder(req);

        // verify
        Assertions.assertEquals(1, repository.count());

        var order = repository.findAll().get(0);
        Assertions.assertEquals("123", order.getReference());
        Assertions.assertEquals(2, order.getOrderLines().size());

    }
}