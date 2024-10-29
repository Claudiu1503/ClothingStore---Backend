package com.backend.clothingstore.services;

import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;

import java.util.List;

public interface OrderService {

    public Order createOrder(Order order);
    public Order getOrderById(int id);
    public List<Order> getAllOrders();
    public Order updateOrder(int id, Order orderDetails);
    public boolean deleteOrder(int id);
    public OrderItem addItemToOrder(int orderId, OrderItem orderItem);
    public boolean removeItemFromOrder(int orderId, int itemId);
}
