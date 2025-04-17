package com.omosman.ecommerce.customer;

import com.omosman.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerResponse createCustomer(CustomerRequest request) {
        return mapper.fromCustomer(repository.save(mapper.toCustomer(request)));
    }

    public CustomerResponse updateCustomer(UpdateCustomerRequest request) throws CustomerNotFoundException {
        var toUpdate = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
                ));

        toUpdate.setEmail(request.email());
        toUpdate.setFirstname(request.firstname());
        toUpdate.setLastname(request.lastname());
        toUpdate.setAddress(request.address());

        return mapper.fromCustomer(repository.save(toUpdate));
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(mapper::fromCustomer).toList();
    }

    /**
    * returns null if customer not found
    * */
    public CustomerResponse getCustomerById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElse(null);
    }

    public void deleteCustomerById(String id) {
        repository.deleteById(id);
    }
}
