package com.backend.clothingstore.DTO;

import lombok.Data;

@Data
public class AddressDTO {
    private String state;
    private String zip;
    private String country;
    private String city;
    private String addressLine;
    private String phone;
}
