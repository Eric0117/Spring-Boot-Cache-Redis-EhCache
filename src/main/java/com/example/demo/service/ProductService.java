package com.example.demo.service;

import com.example.demo.domain.Product;

import java.util.Optional;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
public interface ProductService {

    Optional<Product> getProduct(Long id);
}
