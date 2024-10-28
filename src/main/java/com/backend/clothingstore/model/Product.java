
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
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated
    @Column( nullable = false)
    private Category category;

    @Column( nullable = false)
    private Double price;

    @Column(name = "long_descrption")
    private String longDescrption;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;


    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventory inventory;

}
