package com.mile.persistence_patterns.inefficient_transactions.long_running.bad;

import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public void processOrders(List<Long> orderIds) {
    for (Long orderId : orderIds) {
      Optional<Order> orderOpt = orderRepository.findById(orderId);
      if (orderOpt.isPresent()) {
        validateClusterHealth(); // Simulated delay of 5s
        Order order = orderOpt.get();
        order.setOrderDate(order.getOrderDate().plusDays(1)); // Update order
      }
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
