package com.backend.clothingstore.controllers;

import com.backend.clothingstore.DTO.AddressDTO;
import com.backend.clothingstore.DTO.UserProfileDTO;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/profile")
    public ResponseEntity<User> getAdminProfile(@RequestParam String email) {
        User user = userService.findByuserEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@RequestParam String email) {
        User user = userService.findByuserEmail(email);
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(user.getId());
        userProfileDTO.setUsername(user.getUsername());
        userProfileDTO.setFirstName(user.getFirstName());
        userProfileDTO.setLastName(user.getLastName());
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setRole(user.getRole().toString());
        userProfileDTO.setCreatedAt(user.getCreatedAt());
        userProfileDTO.setAddresses(user.getAddresses());

        return ResponseEntity.ok(userProfileDTO);
    }


    @PutMapping("/admin/update")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        User updatedUser = userService.updateAdminUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileDTO userProfileDTO) {
        User user = userService.updateUser(userProfileDTO);
        return ResponseEntity.ok(userProfileDTO);
    }

    @PostMapping("/user/{userId}/address")
    public ResponseEntity<User> addAddress(@PathVariable int userId, @RequestBody AddressDTO addressDTO) {
        User user = userService.addAddressToUser(userId, addressDTO);
        return ResponseEntity.ok(user);
    }


}

