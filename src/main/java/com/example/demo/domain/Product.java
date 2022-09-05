package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "product_name")
    private String product_name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @PositiveOrZero
    private int price;

    @PositiveOrZero
    private int stock;


}
