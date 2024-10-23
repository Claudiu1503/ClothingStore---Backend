package com.backend.clothingstore.loginRegisterControllers;

import com.backend.clothingstore.DTO.LoginRequestDTO;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Collections;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {

            User newUser = userService.register(user);
            return ResponseEntity.ok(newUser);
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("Email already taken")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        try {
            String message = userService.login(email, password);
            // Returnam un obiect JSON cu un mesaj
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", e.getMessage()));
        }
    }






}
