
package com.backend.clothingstore.model;

import jakarta.persistence.*;
import lombok.*;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ProductGender gender;

    @Enumerated(EnumType.STRING)
    private ProductColor color;
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "long_descrption")
    private String longDescription;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;



}
