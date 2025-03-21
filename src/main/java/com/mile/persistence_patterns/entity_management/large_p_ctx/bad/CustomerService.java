package com.mile.persistence_patterns.entity_management.large_p_ctx.bad;

import com.mile.persistence_patterns.dto.CustomerDto;
import com.mile.persistence_patterns.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getCustomersInfo() {
        return customerRepository
                .findAll()
                .stream()
                .map(c -> new CustomerDto(c.getId(), c.getName()))
                .collect(Collectors.toList());
                // ❌ Fetching full Customer entities and mapping in afterward
    }
}
