package com.mile.persistence_patterns.repository;

import com.mile.persistence_patterns.dto.CustomerDto;
import com.mile.persistence_patterns.dto.CustomerOrderDto;
import com.mile.persistence_patterns.entity.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @Query("SELECT c FROM Customer c JOIN FETCH c.orders")
//    List<Customer> findAllWithOrders();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Optional<Customer> findAndLockById(@Param("id") Long id);

    @EntityGraph(value = "orders", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllWithOrders();

    @Query("SELECT new com.mile.persistence_patterns.dto.CustomerOrderDto(" +
            "c.id, c.name, o.orderDate) " +
            "FROM Customer c JOIN c.orders o")
    List<CustomerOrderDto> findAllCustomerOrders();


    @Query("SELECT new com.mile.persistence_patterns.dto.CustomerDto(c.id, c.name) FROM Customer c")
    List<CustomerDto> findCustomersInfo();
}
