package com.backend.clothingstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "favorites")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
//    @JsonIgnoreProperties({"category", "gender","color", "brand", "price", "quantity", "longDescription", "shortDescription"})
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonIgnoreProperties({"password", "firstName", "email", "lastName", "isVerified", "verificationToken", "createdAt", "updatedAt", "role", "state", "zip", "country", "city", "addressLine", "phone"})
    private User user;

}
