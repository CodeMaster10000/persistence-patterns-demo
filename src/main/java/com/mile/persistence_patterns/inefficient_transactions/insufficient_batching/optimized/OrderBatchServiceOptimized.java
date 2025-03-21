package com.mile.persistence_patterns.inefficient_transactions.insufficient_batching.optimized;

import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderBatchServiceOptimized {

  private final OrderRepository orderRepository;

  public OrderBatchServiceOptimized(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public void createOrdersEfficiently(List<Order> orders) {
    orderRepository.saveAll(orders); // Single batch insert, much faster
  }
}
