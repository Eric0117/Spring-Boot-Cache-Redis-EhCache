package com.example.demo.repository;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
