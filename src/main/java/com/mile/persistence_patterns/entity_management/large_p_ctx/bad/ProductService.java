package com.mile.persistence_patterns.entity_management.large_p_ctx.bad;

import com.mile.persistence_patterns.entity.Product;
import com.mile.persistence_patterns.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional // ❌ Not needed for read operations
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // ❌ Entities are managed unnecessarily
    }
}
