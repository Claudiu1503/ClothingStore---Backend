package com.backend.clothingstore.passwordReset;

import com.backend.clothingstore.model.ConfirmationToken;
import com.backend.clothingstore.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordToken, Integer> {
    Optional<ResetPasswordToken> findByResetPasswordToken(String token);
}
