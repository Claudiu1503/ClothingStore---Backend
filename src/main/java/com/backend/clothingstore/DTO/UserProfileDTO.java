package com.backend.clothingstore.DTO;


import com.backend.clothingstore.model.Address;
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
    private List<Address> addresses;
}