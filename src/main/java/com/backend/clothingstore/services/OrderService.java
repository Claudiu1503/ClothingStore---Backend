package com.backend.clothingstore.services;

import com.backend.clothingstore.DTO.OrderDTO;
import com.backend.clothingstore.DTO.OrderItemDTO;
import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderDTO orderDTO);
    public Order getOrderById(int id);
    public List<Order> getAllOrders();
    public Order updateOrder(int id, OrderDTO orderDTO);
    public boolean deleteOrder(int id);
    public OrderItem addItemToOrder(int orderId, OrderItemDTO orderItemDTO);
    public boolean removeItemFromOrder(int orderId, int itemId);

    List<Order> getAllUserOrders(int id);
}
