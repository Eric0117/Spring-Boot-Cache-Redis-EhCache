package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDTO;

import java.util.Optional;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
public interface ProductService {
    Product getProduct(Long id);
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}