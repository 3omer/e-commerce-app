package com.omosman.ecommerce.product;

import com.omosman.ecommerce.exception.BusinessException;
import com.omosman.ecommerce.order.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public Set<PurchaseResponse> purchaseProducts(Set<PurchaseRequest> request) {
        var headers = new HttpHeaders();

        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Set<PurchaseRequest>> requestEntity = new HttpEntity<>(request, headers);
        ParameterizedTypeReference<Set<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {
                };

        var response = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );

        if (response.getStatusCode().isError()) {
            log.info("Product purchase api error response:{}", response.getStatusCode());
            throw new BusinessException("An error occurred while processing the products purchase");
        }
        return response.getBody();
    }

}
