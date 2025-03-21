package com.mile.persistence_patterns.ineffective_concurrency.high_contention.solution;

import com.mile.persistence_patterns.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.LockMode;
import org.hibernate.query.Query;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    @Retryable(
            value = OptimisticLockException.class, // ✅ Retries if an optimistic lock conflict occurs
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000) // ✅ Wait 1 second before retrying
    )
    @Transactional
    public void updateStockForProducts(int quantity, int batchSize) {
        List<Product> products = getUnlockedProducts(batchSize);

        if (products.isEmpty()) {
            throw new RuntimeException("No available products to process, retrying...");
        }

        for (Product product : products) {
            product.setStock(product.getStock() - quantity);
            entityManager.merge(product); // Save the updated product
        }
    }

    private List<Product> getUnlockedProducts(int batchSize) {
        return entityManager.createQuery("""
                SELECT p FROM Product p WHERE p.stock > 0
                ORDER BY p.id
                """, Product.class)
                .setMaxResults(batchSize) // Fetch a batch of unlocked products
                .unwrap(Query.class) // Access Hibernate's query API
                .setHibernateLockMode(LockMode.UPGRADE_SKIPLOCKED) // Enables SKIP LOCKED
                .getResultList();
    }
}
