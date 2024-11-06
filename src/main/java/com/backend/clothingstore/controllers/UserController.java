package com.backend.clothingstore.controllers;

import com.backend.clothingstore.DTO.UserProfileDTO;
import com.backend.clothingstore.mappers.UserToUserAddress;
import com.backend.clothingstore.model.Address;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.authenticate.registerConfirmation.repository.ConfirmationTokenRepository;
import com.backend.clothingstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private UserToUserAddress userToUserAddress;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

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

        // address
        userProfileDTO.setState(user.getState());
        userProfileDTO.setZip(user.getZip());
        userProfileDTO.setCountry(user.getCountry());
        userProfileDTO.setCity(user.getCity());
        userProfileDTO.setAddressLine(user.getAddressLine());
        userProfileDTO.setPhone(user.getPhone());

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

    @GetMapping("/request/{id}/address")
    public Address getUserAddress(@PathVariable int id) {
        User user = userService.getUserById(id);
        return UserToUserAddress.map(user);
    }

    @DeleteMapping( "/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        confirmationTokenRepository.deleteById(id);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }


}

