package com.backend.clothingstore.services;

import com.backend.clothingstore.DTO.AddressDTO;
import com.backend.clothingstore.DTO.UserProfileDTO;
import com.backend.clothingstore.model.User;



public interface UserService {
    public void save(User user);
    User register(User user);
    String login(String email, String password);

    User authenticate(String email, String password);

    User updateUser(UserProfileDTO userDTO);
    User updateAdminUser(User user);

    User findByuserEmail(String email);
    User addAddressToUser(int userId, AddressDTO addressDTO);
}
