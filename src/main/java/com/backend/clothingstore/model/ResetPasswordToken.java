package com.backend.clothingstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    private String resetPasswordToken;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    private LocalDateTime confirmationDate;

    private boolean used;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ResetPasswordToken(String resetPasswordToken, LocalDateTime creationDate, LocalDateTime expirationDate, LocalDateTime confirmationDate, User user) {
        this.resetPasswordToken = resetPasswordToken;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.confirmationDate = confirmationDate;

        this.user = user;
    }
}
