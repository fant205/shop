package com.alexey.shop.services;

import com.alexey.shop.dto.Product;
import com.alexey.shop.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id);
    }
}
