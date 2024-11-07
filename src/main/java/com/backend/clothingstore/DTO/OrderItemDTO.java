package com.backend.clothingstore.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private int productId;
    private int quantity;
}
