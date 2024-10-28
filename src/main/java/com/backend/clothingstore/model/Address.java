package com.backend.clothingstore.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "useraddress")
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

    @Column(name = "addressline", nullable = false, length = 70)
    private String addressLine;

    @Column(nullable = false, length = 10)
    private String phone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}