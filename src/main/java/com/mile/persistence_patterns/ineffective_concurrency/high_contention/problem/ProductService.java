package com.mile.persistence_patterns.ineffective_concurrency.high_contention.problem;

import com.mile.persistence_patterns.entity.Product;
import com.mile.persistence_patterns.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findAndLockById(productId); // Locked row
        product.setStock(product.getStock() - quantity);
        productRepository.save(product); // Other transactions must wait
    }

}
