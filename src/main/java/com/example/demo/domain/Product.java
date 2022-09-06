package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 5.
 **/
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "product_name")
    private String productName;

    @NotBlank
    @Column(name = "description")
    private String description;

    @PositiveOrZero
    private int price;

    @PositiveOrZero
    private int stock;

    public void updateProduct(String productName, String description, int price, int stock) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }


}
