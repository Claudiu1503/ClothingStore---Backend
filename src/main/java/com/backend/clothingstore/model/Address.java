package com.backend.clothingstore.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Address {

    private String country;
    private String state;
    private String city;
    private String zip;
    private String addressLine;
    private String phone;
}
