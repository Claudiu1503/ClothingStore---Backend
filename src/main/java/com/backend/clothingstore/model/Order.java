package com.backend.clothingstore.model;

import com.backend.clothingstore.DTO.OrderItemDTO;
import com.backend.clothingstore.DTO.OrderResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "userorder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password", "firstName", "email", "lastName", "isVerified", "verificationToken", "createdAt", "updatedAt", "role", "state", "zip", "country", "city", "addressLine", "phone"})
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private Double total;

    // Custom getter to expose only required fields for JSON response
    public OrderResponseDTO toResponseDTO() {
        List<OrderItemDTO> items = orderItems.stream()
                .map(orderItem -> new OrderItemDTO(orderItem.getProduct().getId(), orderItem.getQuantity()))
                .collect(Collectors.toList());

        return new OrderResponseDTO(id, user.getId(), items, total);
    }
}
