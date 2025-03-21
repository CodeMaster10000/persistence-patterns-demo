package com.mile.persistence_patterns.inefficient_transactions.long_running.optimized;

import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceOptimized {

  private final OrderPersistenceService orderPersistenceService;

  public OrderServiceOptimized(OrderPersistenceService orderPersistenceService) {
    this.orderPersistenceService = orderPersistenceService;
  }

  public void processOrdersEfficiently(List<Long> orderIds) {
    List<Order> orders =
        orderPersistenceService.findAllById(orderIds); // Fetch outside of transaction
    validateClusterHealth();// Long-running operation OUTSIDE the transaction
    for (Order order : orders) {
      orderPersistenceService.updateOrderDate(order);
    }
  }

  private void validateClusterHealth() {
    try {
      Thread.sleep(5000); // Simulate a 5s delay
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
