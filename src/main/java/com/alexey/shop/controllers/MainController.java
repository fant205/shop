package com.alexey.shop.controllers;

import com.alexey.shop.dto.Product;
import com.alexey.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/product/all")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/product/change_cost")
    public void changeCost(@RequestParam Long id, @RequestParam Integer cost) {
        productService.changeCost(id, cost);
    }

    @DeleteMapping("/product/delete")
    public void delete(@RequestParam Long id){
        productService.delete(id);
    }
}