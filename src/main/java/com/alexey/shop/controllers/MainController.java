package com.alexey.shop.controllers;

import com.alexey.shop.dto.ProductDTO;
import com.alexey.shop.dto.UserDTO;
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
    public List<ProductDTO> getAllProducts() {
        return shopService.findAllProducts();
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
    public List<ProductDTO> findProductsBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "50") Integer max){
        return shopService.findProductsBetween(min, max);
    }

}