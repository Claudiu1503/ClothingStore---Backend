package com.backend.clothingstore.DTO;

import com.backend.clothingstore.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private int userId;
    private Address address;
    private List<OrderItemDTO> items;

}
