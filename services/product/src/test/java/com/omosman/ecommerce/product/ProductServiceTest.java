package com.omosman.ecommerce.product;

import com.omosman.ecommerce.category.Category;
import com.omosman.ecommerce.exception.InsufficientProductQuantityException;
import com.omosman.ecommerce.exception.ProductNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    /**
    * when called with a valid request
     * should not throw
     * should call the repo to update same products quantities
    * */
    @Test
    public void purchaseProductsSuccessfully_whenRequestIsValid(){

        var category = new Category(1, "c", new ArrayList<>());

        var productA = new Product(1, "a", "a", 10,  new BigDecimal(100), category);
        var productB = new Product(2, "b", "b", 10,  new BigDecimal(100), category);

        var purchaseRequest = List.of(new PurchaseRequest(1, 1), new PurchaseRequest(2, 10));

        Mockito.when(repository.findAllById(List.of(1, 2))).thenReturn(List.of(productA, productB));

        service.processPurchase(purchaseRequest);

        // assert
        Assertions.assertEquals(9, productA.getAvailableQuantity());
        Assertions.assertEquals(0, productB.getAvailableQuantity());

        Mockito.verify(repository).saveAll(List.of(productA, productB));

    }

    /**
     * call purchaseProcess() with non-existing ids
     * find by ids will return existing ids
     * */
    @Test
    public void shouldThrowProductNotFoundException_whenSomeProductsAreMissing(){
        var category = new Category(1, "c", new ArrayList<>());

        var productA = new Product(1, "a", "a", 10,  new BigDecimal(100), category);
        var productB = new Product(2, "b", "b", 10,  new BigDecimal(100), category);

        var purchaseRequest = List.of(
                new PurchaseRequest(1, 1),
                new PurchaseRequest(2, 10),
                new PurchaseRequest(3, 10)
                );

        Mockito.when(repository.findAllById(List.of(1,2,3))).thenReturn(List.of(productA, productB));

        Assertions.assertThrows(ProductNotFoundException.class,
                () -> service.processPurchase(purchaseRequest));

    }

    @Test
    public void shouldThrowInsufficientQuantityException_whenSomeQuantitiesAreLow(){
        var category = new Category(1, "c", new ArrayList<>());

        var productA = new Product(1, "a", "a", 10,  new BigDecimal(100), category);
        var productB = new Product(2, "b", "b", 10,  new BigDecimal(100), category);

        var purchaseRequest = List.of(
                new PurchaseRequest(1, 11),
                new PurchaseRequest(2, 10)
        );

        Mockito.when(repository.findAllById(List.of(1,2))).thenReturn(List.of(productA, productB));

        Assertions.assertThrows(InsufficientProductQuantityException.class,
                () -> service.processPurchase(purchaseRequest));

    }
}