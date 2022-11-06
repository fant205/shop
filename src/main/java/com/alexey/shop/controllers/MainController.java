package com.alexey.shop.controllers;

import com.alexey.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/products")
    public String showAllProducts(Model model) {
        model.addAttribute("productsList", productService.findAllProducts());
        return "index.html";
    }

    @GetMapping("/product")
    public String page(Model model, @RequestParam Long id){
        model.addAttribute("product", productService.findProductById(id));
        return "product.html";
    }
}