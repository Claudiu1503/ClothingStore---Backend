package com.backend.clothingstore.passwordReset;

import com.backend.clothingstore.DTO.EmailDTO;
import com.backend.clothingstore.DTO.TokenPasswordDTO;
import com.backend.clothingstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/reset")
public class ResetPasswordController {

    @Autowired
    ResetPasswordService resetPasswordService;

    //Request password change
    @PostMapping("/request/password-change")
    public ResponseEntity<?> changePassword(@RequestBody EmailDTO email) {

      return resetPasswordService.requestResetPassword(email);

    }

    //Confirm the code, verify if it hasn't expired, and reset the password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody TokenPasswordDTO tokenPasswordDTO) {
        boolean isResetSuccessful = resetPasswordService.resetPassword(
                tokenPasswordDTO.getToken(),
                tokenPasswordDTO.getNewPassword()
        );

        if (isResetSuccessful) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }




}
