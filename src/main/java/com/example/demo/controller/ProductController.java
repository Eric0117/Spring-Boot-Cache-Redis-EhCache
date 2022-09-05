package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
//    @Value("${test.value}")
//    private String value;

    private final ProductService productService;

    @GetMapping("/{productId}")
    public Optional<Product> test(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }
}
