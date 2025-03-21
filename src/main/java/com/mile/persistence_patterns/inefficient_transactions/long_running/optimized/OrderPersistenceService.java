package com.mile.persistence_patterns.inefficient_transactions.long_running.optimized;

import com.mile.persistence_patterns.entity.Order;
import com.mile.persistence_patterns.repository.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPersistenceService {

  private final OrderRepository orderRepository;

  public OrderPersistenceService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }


  List<Order> findAllById(List<Long> orderIds) {
    return orderRepository.findAllById(orderIds);
  }

  public void updateOrderDate(Order order) {
    order.setOrderDate(order.getOrderDate().plusDays(1));
    orderRepository.save(order); // Transaction is now very short
  }
}
