package com.example.demo.service.impl;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
}
