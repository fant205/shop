package com.alexey.shop.services;

import com.alexey.shop.convertor.ProductConvertor;
import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.dto.ProductsGetDto;
import com.alexey.shop.model.Product;
import com.alexey.shop.repository.ProductsRepository;
import com.alexey.shop.repository.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ProductsRepository productsRepository;
    private final ProductConvertor productConvertor;

    public ProductDto findProductById(Long id) {
        Product product = productsRepository.findById(id).orElseThrow();
        return new ProductDto(product.getId(), product.getTitle(), product.getCost());
    }

    public ProductsGetDto findAllProducts(Long id, String title, Integer min, Integer max, Integer page, Integer size) {
        Specification<Product> spec = Specification
                .where(id == null ? null : ProductSpecification.equalId(id))
                .and(StringUtils.hasText(title) ? ProductSpecification.likeTitle(title) : null)
                .and(min == null ? null : ProductSpecification.greaterThanOrEqualToScore(min))
                .and(max == null ? null : ProductSpecification.lessThanOrEqualToScore(max));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        Page<Product> products = productsRepository.findAll(spec, pageRequest);
        List<ProductDto> dtos = products.map(product -> productConvertor.convertToDto(product)).toList();
        return new ProductsGetDto(dtos, products.getTotalPages(), products.getTotalElements());
    }

    @Transactional
    public void save(ProductDto productDTO) {
        Product product = Product.builder()
                .id(productDTO.getId())
                .title(productDTO.getTitle())
                .cost(productDTO.getCost()).build();
        productsRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = Product.builder().id(id).build();
        productsRepository.delete(product);
    }

//    public List<ProductDTO> findProductsBetween(Integer min, Integer max) {
//        List<Product> list = productsRepository.findProductsBetween(min, max);
//        return list.stream().map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getCost())).collect(Collectors.toList());
//    }

//    public void changeCost(Integer cost, Long id) {
//        Product product = productsRepository.findById(id).get();
//        product.setCost(product.getCost() + cost);
//        productsRepository.save(product);
//    }


}
