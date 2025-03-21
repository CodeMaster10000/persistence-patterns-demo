package com.mile.persistence_patterns.inefficient_transactions.transaction_propagation.optimized;

import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOrder(Order order) {
        orderRepository.save(order); // Starts an independent transaction
    }
}
