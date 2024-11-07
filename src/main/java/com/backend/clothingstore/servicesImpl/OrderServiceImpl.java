package com.backend.clothingstore.servicesImpl;

import com.backend.clothingstore.DTO.OrderDTO;
import com.backend.clothingstore.DTO.OrderItemDTO;
import com.backend.clothingstore.errorHeandler.InsufficientStockException;
import com.backend.clothingstore.model.Order;
import com.backend.clothingstore.model.OrderItem;
import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.repositories.OrderItemRepository;
import com.backend.clothingstore.repositories.OrderRepository;
import com.backend.clothingstore.repositories.ProductRepository;
import com.backend.clothingstore.repositories.UserRepository;
import com.backend.clothingstore.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws InsufficientStockException {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);


        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < itemDTO.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            total += product.getPrice() * itemDTO.getQuantity();
            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        return order;
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
    public List<Order> getAllUserOrders(int id) {
     return orderRepository.findByUserId(id);
    }


    @Override
    @Transactional
    public Order updateOrder(int id, OrderDTO orderDTO) throws InsufficientStockException {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;


        List<OrderItem> updatedOrderItems = new ArrayList<>();
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < itemDTO.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            updatedOrderItems.add(orderItem);

            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }

        order.setOrderItems(updatedOrderItems);
        orderRepository.save(order);

        return order;
    }

    @Override
    @Transactional
    public boolean deleteOrder(int id) {
        if (!orderRepository.existsById(id)) return false;
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public OrderItem addItemToOrder(int orderId, OrderItemDTO orderItemDTO) throws InsufficientStockException {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;

        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < orderItemDTO.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product " + product.getName());
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDTO.getQuantity());

        order.getOrderItems().add(orderItem);
        orderItemRepository.save(orderItem);

        product.setQuantity(product.getQuantity() - orderItemDTO.getQuantity());
        productRepository.save(product);

        return orderItem;
    }

    @Override
    @Transactional
    public boolean removeItemFromOrder(int orderId, int itemId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return false;

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
        if (orderItem == null) return false;

        order.getOrderItems().remove(orderItem);
        orderItemRepository.delete(orderItem);

        return true;
    }


}


