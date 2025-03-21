package com.mile.persistence_patterns.n1_query_problem.problem;

import com.mile.persistence_patterns.entity.Customer;
import com.mile.persistence_patterns.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomersWithOrders() {
        List<Customer> customers = customerRepository.findAll(); // ❌ 1 Query for Customers

        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.getName()
                            + " Orders: "
                            + customer.getOrders().size()); // ❌ N Queries for Orders
        }
        return customers;
    }

}
