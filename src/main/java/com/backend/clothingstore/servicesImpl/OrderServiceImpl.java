package com.backend.clothingstore.servicesImpl;

import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;
import com.backend.clothingstore.repositories.OrderItemRepository;
import com.backend.clothingstore.repositories.OrderRepository;
import com.backend.clothingstore.repositories.UserRepository;
import com.backend.clothingstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(int id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setUser(orderDetails.getUser());
            order.setAddress(orderDetails.getAddress());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public boolean deleteOrder(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public OrderItem addItemToOrder(int orderId, OrderItem orderItem) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            orderItem.setOrder(order);
            return orderItemRepository.save(orderItem);
        }
        return null;
    }

    @Override
    public boolean removeItemFromOrder(int orderId, int itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElse(null);
        if (orderItem != null && orderItem.getOrder().getId() == orderId) {
            orderItemRepository.delete(orderItem);
            return true;
        }
        return false;
    }

}
