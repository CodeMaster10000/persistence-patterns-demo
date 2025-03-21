package com.mile.persistence_patterns.inefficient_transactions.transaction_propagation.bad;

import com.mile.persistence_patterns.entity.Customer;
import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerOrderService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    public CustomerOrderService(CustomerRepository customerRepository, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    @Transactional
    public void createCustomerAndOrders(Customer customer, Order order) {
        customerRepository.save(customer); // Transaction starts here
        orderService.createOrder(order); //  Runs inside the same transaction
    }
}
