package com.mile.persistence_patterns.ineffective_concurrency.deadlocks.solution;

import com.mile.persistence_patterns.entity.Customer;
import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.CustomerRepository;
import com.mile.persistence_patterns.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(timeout = 5) // Fails if the transaction takes too long
    public void updateCustomer(Long id, String newName) {
        Customer customer = customerRepository.findAndLockById(id)
                .orElseThrow();
        customer.setName(newName);
        customerRepository.save(customer);
    }

    @Transactional
    public void processTransaction(Long customerId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(); // Lock Order
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        orderRepository.flush(); // Commit early (Releases lock)

        Customer customer = customerRepository
                .findById(customerId).orElseThrow(); // Fetch customer AFTER committing
    }

    @Transactional
    public void updateCustomerName(Long customerId, String name) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.setName(name);
        customerRepository.save(customer);
    }

}
