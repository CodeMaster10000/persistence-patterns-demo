package com.mile.persistence_patterns.choosing_isolation.dirty_reads.solution;

import com.mile.persistence_patterns.entity.Product;
import com.mile.persistence_patterns.repository.OrderRepository;
import com.mile.persistence_patterns.repository.ProductRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DemoService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public DemoService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void processTransaction() {
        // This transaction will NOT see uncommitted data from others
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void buyProduct(long productId, int stock) {
        Optional<Product> prodOpt = productRepository.findById(productId);
        if (prodOpt.isPresent()) {
            Product product = prodOpt.get();
            int available = product.getStock() - stock;
            if (available > -1) {
                product.setStock(product.getStock() - stock);
            } else {
                throw new RuntimeException("not enough in stock");
            }
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void calculateRevenue() {
        int revenue1 = orderRepository.calculateTotalRevenue(); // First Read
        someExternalWork();// Simulated delay
        int revenue2 = orderRepository.calculateTotalRevenue(); // Second Read

        if (revenue1 != revenue2) {
            throw new RuntimeException("Inconsistent revenue data!");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Product> getAvailableProducts() {
        return productRepository.findAll();
        //process the products
    }


    private void someExternalWork() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
