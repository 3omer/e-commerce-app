package com.omosman.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    // create product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid ProductRequest productRequest

    ) {
        log.info("Creating new product");
        var productResponse = service.createProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    /**
    * return 200 only if the purchased quantities are available
    * */
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchaseHandler(
            @RequestBody @Valid List<PurchaseRequest> request
    ){
        service.processPurchase(request);
        return ResponseEntity.ok(new PurchaseResponse());
    }
}
