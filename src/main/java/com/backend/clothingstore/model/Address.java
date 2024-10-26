package com.backend.clothingstore.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country", nullable = false, length = 75)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "address_line_1", nullable = false, length = 512)
    private String addressLine1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}