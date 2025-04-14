package com.omosman.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 10) @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        var products = service.filterProducts(name, category, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(products);
    }

    // create product
    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest productRequest

    ) {
        log.info("Creating new product");
        var productId = service.createProduct(productRequest);
        return ResponseEntity.ok(productId);
    }

    /**
     * return 200 only if the purchased quantities are available
     */
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchaseHandler(
            @RequestBody @Valid List<PurchaseRequest> request
    ) {
        service.processPurchase(request);
        return ResponseEntity.ok(new PurchaseResponse());
    }
}
