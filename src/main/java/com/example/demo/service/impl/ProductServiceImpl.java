package com.example.demo.service.impl;

import com.example.demo.config.CacheConfig;
import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final CacheConfig cacheConfig;

    @Override
    @Cacheable(cacheNames = "ProductCache", key = "#id")
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    @Override
    @CachePut(cacheNames = "ProductCache", key = "#result.id")
    public Product createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .build();

        return productRepository.save(product);
    }

    @Override
    @CacheEvict(cacheNames = "ProductCache", key = "#id")
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));

        product.updateProduct(productDTO.getProductName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getStock());
        productRepository.save(product);

        return product;
    }

    @Override
    @CacheEvict(cacheNames = "ProductCache", key = "#id") // 해당 캐시 삭제
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
        productRepository.delete(product);
    }
}
