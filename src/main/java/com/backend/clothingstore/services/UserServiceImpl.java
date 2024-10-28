package com.backend.clothingstore.services;

import com.backend.clothingstore.errorHeandler.UserNotFoundException;
import com.backend.clothingstore.model.Role;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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
    public User updateUser(User user) {
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

        //Doar pentru roll cand testezi cu postman
//        if (user.getRole() != null) existingUser.setRole(user.getRole());


//        if (user.getIsVerified() != null) existingUser.setIsVerified(user.getIsVerified());
//        if (user.getVerificationToken() != null) existingUser.setVerificationToken(user.getVerificationToken());

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
