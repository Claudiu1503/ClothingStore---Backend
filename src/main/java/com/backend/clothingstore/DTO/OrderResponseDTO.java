package com.backend.clothingstore.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDTO {
    private int orderId;
    private int userId;
    private List<OrderItemDTO> items;
    private Double total;
}
