
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

    @Enumerated
    @Column(name = "categorie", nullable = false)
    private Categorie categorie;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "long_descrption")
    private String longDescrption;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventory inventory;

}
