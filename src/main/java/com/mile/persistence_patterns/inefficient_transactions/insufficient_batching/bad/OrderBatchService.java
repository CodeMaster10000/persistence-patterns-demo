package com.mile.persistence_patterns.inefficient_transactions.insufficient_batching.bad;

import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderBatchService {

  private final OrderRepository orderRepository;

  public OrderBatchService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public void createOrders(List<Order> orders) {
    for (Order order : orders) {
      orderRepository.save(order); // Each call creates a new transaction
    }
  }
}
