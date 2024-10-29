package com.backend.clothingstore.services;

import com.backend.clothingstore.DTO.AddressDTO;
import com.backend.clothingstore.DTO.UserProfileDTO;
import com.backend.clothingstore.errorHeandler.UserNotFoundException;
import com.backend.clothingstore.model.Address;
import com.backend.clothingstore.model.Role;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.repositories.AddressRepository;
import com.backend.clothingstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private AddressRepository addressRepository;

    @Override
    public User addAddressToUser(int userId, AddressDTO addressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Address newAddress = Address.builder()
                .state(addressDTO.getState())
                .zip(addressDTO.getZip())
                .country(addressDTO.getCountry())
                .city(addressDTO.getCity())
                .addressLine(addressDTO.getAddressLine())
                .phone(addressDTO.getPhone())
                .user(user)
                .build();

        user.getAddresses().add(newAddress);
        addressRepository.save(newAddress); // Salvăm noua adresă
        return user;
    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return "Logged in successfully";
    }

    @Override
    public User authenticate(String email, String password) {
        // Attempt to find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // Check if the user exists and if the password matches
        if (optionalUser.isPresent()) { // Check if the user was found
            User user = optionalUser.get(); // Get the User object
            // Check the provided password against the stored hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user; // Authentication successful
            }
        }

        return null; // Authentication failed
    }

    @Override
    public User updateUser(UserProfileDTO userDTO) {

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userDTO.getId()));

        // Actualizăm câmpurile din entitatea User cu datele din DTO, doar cele specifice
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        // Salvăm modificările în baza de date
        return userRepository.save(user);
    }

    @Override
    public User updateAdminUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update only non-null fields
        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null) {

            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

//        if (user.getPhone() != null) existingUser.setPhone(user.getPhone());
//        if (user.getAddress() != null) existingUser.setAddress(user.getAddress());
//        if (user.getCity() != null) existingUser.setCity(user.getCity());
//        if (user.getState() != null) existingUser.setState(user.getState());
//        if (user.getZip() != null) existingUser.setZip(user.getZip());
//        if (user.getCountry() != null) existingUser.setCountry(user.getCountry());

//        Doar pentru roll cand testezi cu postman
        if (user.getRole() != null) existingUser.setRole(user.getRole());


//        if (user.getIsVerified() != null) existingUser.setIsVerified(user.getIsVerified());
//        if (user.getVerificationToken() != null) existingUser.setVerificationToken(user.getVerificationToken());


        existingUser.setUpdatedAt(LocalDateTime.now());
        // Save and return the updated user
        return userRepository.save(existingUser);
    }


    @Override
    public User findByuserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already in use");
        }

        // Criptăm parola
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsVerified(false); // Inițial nu este verificat

        user.setRole(Role.USER); // by default e user

        User savedUser = userRepository.save(user);

        return savedUser;
    }

}
