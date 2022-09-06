package com.example.demo.dto;

import lombok.Data;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 6.
 **/
@Data
public class ProductDTO {
    String productName;
    String description;
    int price;
    int stock;
}
