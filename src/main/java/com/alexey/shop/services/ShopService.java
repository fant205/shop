package com.alexey.shop.services;

import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.model.Product;
import com.alexey.shop.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ProductsRepository productsRepository;

    public ProductDTO findProductById(Long id) {
        Product product = productsRepository.findById(id).orElseThrow();
        ProductDTO productDTO = new ProductDTO(product.getId(), product.getTitle(), product.getCost());
        return productDTO;
    }

    public List<ProductDTO> findAllProducts() {
        List<Product> list = productsRepository.findAll();
        List<ProductDTO> productDTOS = list.stream().map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getCost())).collect(Collectors.toList());
        return productDTOS;
    }

    @Transactional
    public void add(ProductDTO productDTO) {
        Product product = Product.builder()
                .id(productDTO.getId())
                .title(productDTO.getTitle())
                .cost(productDTO.getCost()).build();
        productsRepository.save(product);
    }

    @Transactional
    public void delete(Long id){
        Product product = Product.builder().id(id).build();
        productsRepository.delete(product);
    }
}
