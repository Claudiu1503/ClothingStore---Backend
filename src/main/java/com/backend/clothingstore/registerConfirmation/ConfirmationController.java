package com.backend.clothingstore.registerConfirmation;


import com.backend.clothingstore.model.ConfirmationToken;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ConfirmationController {

    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    UserService userService;

    @DeleteMapping("/admin/delete-token/{id}")
    public ResponseEntity<?> deleteToken(@PathVariable int id) {
        confirmationTokenService.deleteToken(id);
        return ResponseEntity.ok("Token deleted successfully");
    }

    @GetMapping("/user-confirmation")
    public ResponseEntity<?> registerConfirmation(@RequestParam("token") String token) {
        // Step 1: Fetch the confirmation token
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token)
                .orElse(null);

        // Step 2: Check if token is null (not found)
        if (confirmationToken == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        // Step 3: Check if the token has expired
        if (confirmationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired");
        }


        User user = confirmationToken.getUser();
        //Step 4 verificare daca useru e confirmat deja
        if(user.getIsVerified()==true) {
            return ResponseEntity.badRequest().body("User is already verified");
        }

        // Step 5: Confirm the token
        user.setIsVerified(true);
        userService.save(user);

        // Optional: Update confirmation date in the token
        confirmationToken.setConfirmationDate(LocalDateTime.now());
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return ResponseEntity.ok().body("Account has been successfully confirmed");
    }


}

