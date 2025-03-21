package com.mile.persistence_patterns.repository;

import com.mile.persistence_patterns.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Long> {
    int calculateTotalRevenue();
}
