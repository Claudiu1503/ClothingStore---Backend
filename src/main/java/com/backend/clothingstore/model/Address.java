package com.backend.clothingstore.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "user_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    private String zip;

    @Column(nullable = false, length = 75)
    private String country;

    @Column(nullable = false, length = 75)
    private String city;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "address_line_1", nullable = false, length = 512)
    private String addressLine1;

    @Column(nullable = false, length = 10)
    private String phone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}