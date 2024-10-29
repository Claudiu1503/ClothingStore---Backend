package com.backend.clothingstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NonNull
  @NotBlank(message = "Name is required")
  private String username;


  private String firstName;
  private String lastName;

  @NonNull
  @Email(message = "Please provide a valid email address")
  @NotBlank(message = "Email is required")
  @Column(unique = true)
  private String email;


  @NonNull
  @NotBlank(message = "Password is required")
  private String password;


  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  private String state;
  private String zip;
  private String country;
  private String city;
  private String addressLine;
  private String phone;


  private Boolean isVerified;
  private String verificationToken;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;


  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = LocalDateTime.now();
  }


}
