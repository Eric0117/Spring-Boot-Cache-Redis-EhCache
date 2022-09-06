package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        System.out.println("getProduct 호출");
        return productService.getProduct(productId);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductDTO productDTO) {
        System.out.println("createProduct 호출");
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        System.out.println("updateProduct 호출");
        return productService.updateProduct(productId, productDTO);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        System.out.println("deleteProduct 호출");
        productService.deleteProduct(productId);
    }
}
