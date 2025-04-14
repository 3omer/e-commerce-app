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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Spy
    private ProductMapper mapper = new ProductMapper();

    @InjectMocks
    private ProductService service;


    /**
    * when called with a valid request
     * should not throw
     * should call the repo to update same products quantities
    * */
    @Test
    public void processPurchase_purchaseProductsSuccessfully_whenRequestIsValid(){

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
    public void processPurchase_shouldThrowProductNotFoundException_whenSomeProductsAreMissing(){
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
    public void processPurchase_shouldThrowInsufficientQuantityException_whenSomeQuantitiesAreLow(){
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

    /**
     * what we want to test?
     * repository is called with the correct parameters: verify
     * method return mapped data:
     * */
    @Test
    public void filterProducts_shouldReturnMappedList() {

        var name = "prod";
        var categoryId = 1;
        var minPrice = BigDecimal.valueOf(10);
        var maxPrice = BigDecimal.valueOf(100);
        var pageable = PageRequest.of(0, 2);

        var categoryA = new Category(1, "category a", new ArrayList<>());
        var productA = new Product(1, "product a", "desc", 10, BigDecimal.valueOf(10), categoryA);
        var productB = new Product(2, "product b", "desc", 10, BigDecimal.valueOf(100), categoryA);



        Page<Product> productPage = new PageImpl<Product>(List.of(productA, productB), pageable, 2);

        // mock behaviour
        Mockito.when(repository.findByFilter(name, categoryId, minPrice, maxPrice, pageable))
                .thenReturn(productPage);

        // act
        var result = service.filterProducts(name, categoryId, minPrice, maxPrice, pageable);

        // assert returned data
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(0).name(), "product a");

        // verify repo is called with the right params
        Mockito.verify(repository).findByFilter(name, categoryId, minPrice, maxPrice, pageable);

    }

}