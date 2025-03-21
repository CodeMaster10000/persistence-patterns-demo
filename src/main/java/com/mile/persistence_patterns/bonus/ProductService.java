package com.mile.persistence_patterns.bonus;

import com.mile.persistence_patterns.entity.Product;
import com.mile.persistence_patterns.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Retryable(
            value = OptimisticLockException.class, // Retries only for OptimisticLockException
            maxAttempts = 3, // Retry up to 3 times
            backoff = @Backoff(delay = 500) // Wait 500ms before retrying
    )
    @Transactional
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new RuntimeException("Not enough stock!");
        }

        product.setStock(newStock);
        productRepository.save(product); // If version mismatch, retry mechanism triggers
    }
    
}
