package com.alexey.shop.repo;

import com.alexey.shop.dto.Product;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> list;

    @PostConstruct
    public void init() {
        list = new ArrayList<>(Arrays.asList(
                new Product(1L, "mouse", 2),
                new Product(2L, "keyboard", 3),
                new Product(3L, "monitor", 10)
        ));
    }

    public List<Product> findAllProducts() {
        return list;
    }

    public Product findById(Long id) {
        return list.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Продукт не найден!"));
    }

    public void delete(Long id){
        Product product = findById(id);
        list.remove(product);
    }

}