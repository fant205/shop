package com.alexey.shop.controllers;

import com.alexey.shop.dto.ProductDto;
import com.alexey.shop.dto.ProductsGetDto;
import com.alexey.shop.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ShopService shopService;

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ProductDto findUsersByProductId(@PathVariable Long id) {
        return shopService.findProductById(id);
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ProductsGetDto getAllProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        return shopService.findAllProducts(id, title, min, max, page, size);
    }

    @PostMapping
    @Secured({"ROLE_MANAGER"})
    public void create(@RequestBody ProductDto productDTO) {
        shopService.create(productDTO);
    }

    @PutMapping
    @Secured({"ROLE_MANAGER"})
    public void update(@RequestBody ProductDto productDto) {
        shopService.update(productDto);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_MANAGER"})
    public void delete(@PathVariable Long id) {
        shopService.delete(id);
    }

}