package com.alexey.shop.repository;

import com.alexey.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.cost >= :min and p.cost <= :max")
    List<Product> findProductsBetween(Integer min, Integer max);
}
