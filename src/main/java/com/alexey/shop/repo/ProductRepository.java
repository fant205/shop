package com.alexey.shop.repo;

import com.alexey.shop.dto.ProductDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository {

    private List<ProductDTO> list;

    @PostConstruct
    public void init() {
        list = new ArrayList<>(Arrays.asList(
                new ProductDTO(1L, "mouse", 2),
                new ProductDTO(2L, "keyboard", 3),
                new ProductDTO(3L, "monitor", 10)
        ));
    }

    public List<ProductDTO> findAllProducts() {
        return list;
    }

    public ProductDTO findById(Long id) {
        return list.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Продукт не найден!"));
    }

    public void delete(Long id){
        ProductDTO product = findById(id);
        list.remove(product);
    }

}