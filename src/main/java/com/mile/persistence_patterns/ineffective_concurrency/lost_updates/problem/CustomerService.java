package com.mile.persistence_patterns.ineffective_concurrency.lost_updates.problem;

import com.mile.persistence_patterns.entity.Customer;
import com.mile.persistence_patterns.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void updateCustomer(Long id, String newName) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.setName(newName);
        customerRepository.save(customer); // ❌ No concurrency protection
    }

}
