package com.alexey.shop.services;

import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.dto.ProductsAllDTO;
import com.alexey.shop.model.Product;
import com.alexey.shop.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return new ProductDTO(product.getId(), product.getTitle(), product.getCost());
    }

    public ProductsAllDTO findAllProducts(Integer min, Integer max, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
//        Page<Product> products = productsRepository.findAll(pageRequest);
        Page<Product> products = productsRepository.findProductsAll(min, max, pageRequest);
        List<ProductDTO> dtos = products.stream().map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getCost())).collect(Collectors.toList());
        return new ProductsAllDTO(dtos, products.getTotalPages(), products.getTotalElements());
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

    public List<ProductDTO> findProductsBetween(Integer min, Integer max){
        List<Product> list = productsRepository.findProductsBetween(min, max);
        return list.stream().map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getCost())).collect(Collectors.toList());
    }

    public void changeCost(Integer cost, Long id) {
        Product product = productsRepository.findById(id).get();
        product.setCost(product.getCost() + cost);
        productsRepository.save(product);
    }
}
