package com.omosman.ecommerce.product;

import com.omosman.ecommerce.exception.InsufficientProductQuantityException;
import com.omosman.ecommerce.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest productRequest) {
        var product = mapper.toProduct(productRequest);
        return repository.save(product).getId();
    }

    /**
     * purchase and update quantities in the warehouse
     * throws InsufficientQuantityError, ProductNotFoundError
     */
    public void processPurchase(List<PurchaseRequest> request) throws InsufficientProductQuantityException, ProductNotFoundException {
        var productsPurchaseIds = request.stream().map(PurchaseRequest::id).toList();

        var foundProducts = repository.findAllById(productsPurchaseIds);
        var foundProductsIds = foundProducts.stream().map(Product::getId).toList();

        var notFoundIds = productsPurchaseIds.stream()
                .filter(id -> !foundProductsIds.contains(id))
                .toList();

        if (!notFoundIds.isEmpty()) {
            throw new ProductNotFoundException(String.format("Cannot purchase product(s) with provided IDs: {%s}", notFoundIds));
        }

        Map<Integer, Integer> requestedQuantities = request.stream()
                .collect(Collectors.toMap(PurchaseRequest::id, PurchaseRequest::quantity));

        // quantity checks
        var notAvailableQuantities = foundProducts.stream()
                .filter(product -> product.getAvailableQuantity() < requestedQuantities.get(product.getId()))
                .toList();

        if (!notAvailableQuantities.isEmpty()) {
            throw new InsufficientProductQuantityException("Cannot purchase product(s), not available quantity(s)");
        }

        // update quantities
        var updatedProducts = foundProducts.stream()
                .peek(product -> product.setAvailableQuantity(product.getAvailableQuantity() - requestedQuantities.get(product.getId())))
                .toList();

        repository.saveAll(updatedProducts);
    }

    public List<ProductResponse> filterProducts(String name, Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable page) {

        var products = repository.findByFilter(
                        name,
                        categoryId,
                        minPrice,
                        maxPrice,
                        page
                )
                .map(mapper::fromProduct);

        return products.toList();
    }


}
