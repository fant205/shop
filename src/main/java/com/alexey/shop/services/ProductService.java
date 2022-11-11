package com.alexey.shop.services;

import com.alexey.shop.dao.ProductDao;
import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<ProductDTO> findAllProducts() {
        List<Product> all = productDao.findAll();
        List<ProductDTO> result = new ArrayList<>();
        all.forEach(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setTitle(product.getTitle());
            dto.setCost(product.getCost());
            result.add(dto);
        });
        return result;
    }

    public ProductDTO findProductById(Long id) {
        Product product = productDao.findById(id);
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setCost(product.getCost());
        return dto;
    }

    public void changeCost(Long id, Integer cost) {
        Product product = productDao.findById(id);
        product.setCost(product.getCost() + cost);
        productDao.saveOrUpdate(product);
    }

    public void delete(Long id) {
        productDao.deleteById(id);
    }
}
