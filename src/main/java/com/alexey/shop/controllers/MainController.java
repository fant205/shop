package com.alexey.shop.controllers;

import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.dto.ProductsAllDTO;
import com.alexey.shop.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ShopService shopService;

    @GetMapping("/products/{id}")
    public ProductDTO findUsersByProductId(@PathVariable Long id) {
        return shopService.findProductById(id);
    }

    @GetMapping("/products/all")
    public ProductsAllDTO getAllProducts(
            @RequestParam(defaultValue = "0") Integer min,
            @RequestParam(defaultValue = "100") Integer max,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        return shopService.findAllProducts(min, max, page, size);
    }

    @PostMapping("/products")
    public void create(@RequestBody ProductDTO productDTO) {
        shopService.add(productDTO);
    }

    @DeleteMapping("/products/delete/{id}")
    public void delete(@PathVariable Long id) {
        shopService.delete(id);
    }

    @GetMapping("/products/between")
    public List<ProductDTO> findProductsBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "99") Integer max) {
        return shopService.findProductsBetween(min, max);
    }

    @GetMapping("/products/change_cost")
    public void changeCost(@RequestParam Integer cost, @RequestParam Long id) {
        shopService.changeCost(cost, id);
    }

}