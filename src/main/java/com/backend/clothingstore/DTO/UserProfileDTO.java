package com.backend.clothingstore.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserProfileDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private String state;
    private String zip;
    private String country;
    private String city;
    private String addressLine;
    private String phone;

}